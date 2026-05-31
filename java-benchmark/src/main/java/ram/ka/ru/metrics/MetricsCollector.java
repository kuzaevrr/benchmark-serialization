package ram.ka.ru.metrics;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MetricsCollector {
    private static volatile boolean running = true;
    private static Thread collectorThread;
    private static long peakHeap = 0;
    private static long totalAllocated = 0;
    private static long gcCount = 0;
    private static long gcTime = 0;

    public static void start() {
        collectorThread = new Thread(() -> {
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            while (running) {
                // Heap memory
                MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
                long usedHeap = heapUsage.getUsed();
                if (usedHeap > peakHeap) peakHeap = usedHeap;

                // Allocated bytes (estimated via delta? simpler: get total comitted?)
                // Для точности аллокаций лучше использовать -XX:NativeMemoryTracking, здесь упрощённо
                totalAllocated += (heapUsage.getCommitted() - usedHeap); // не точно, но для демо

                // GC stats
                long gcCountCurrent = 0, gcTimeCurrent = 0;
                for (var gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
                    gcCountCurrent += gcBean.getCollectionCount();
                    gcTimeCurrent += gcBean.getCollectionTime();
                }
                gcCount = gcCountCurrent;
                gcTime = gcTimeCurrent;

                // CPU load
                double cpuLoad = osBean.getProcessCpuLoad() * 100;

                // Вывод в консоль или лог
                System.out.printf("[METRICS] Heap: %.2f MB, Peak: %.2f MB, CPU: %.2f%%, GC count: %d, GC time: %d ms%n",
                        usedHeap / 1024.0 / 1024, peakHeap / 1024.0 / 1024, cpuLoad, gcCount, gcTime);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        collectorThread.setDaemon(true);
        collectorThread.start();
    }

    public static void stopAndPrint() {
        running = false;
        try {
            collectorThread.join(3000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("=== Final Metrics ===");
        System.out.println("Peak heap memory: " + peakHeap / 1024 / 1024 + " MB");
        System.out.println("Estimated total allocated: " + totalAllocated / 1024 / 1024 + " MB");
        System.out.println("Total GC count: " + gcCount);
        System.out.println("Total GC time: " + gcTime + " ms");
    }
}
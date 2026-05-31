package ram.ka.ru;


import ram.ka.ru.metrics.MetricsCollector;
import ram.ka.ru.model.User;
import ram.ka.ru.formats.*;

import java.util.concurrent.*;

public class LoadTest {
    private static final int CONCURRENCY = 1000;
    private static final int REQUESTS_PER_WORKER = 10000;
    private static final User TEST_USER = User.createTestUser();

    public static void main(String[] args) throws Exception {
        // Определяем формат из аргумента командной строки
        String formatName = args.length > 0 ? args[0] : "json";
        SerializationFormat format;
        switch (formatName.toLowerCase()) {
            case "json":
                format = new JsonFormat();
                break;
            case "xml":
                format = new XmlFormat();
                break;
            case "protobuf":
                format = new ProtobufFormat();
                break;
            case "flatbuffers":
                format = new FlatbufFormat();
                break;
            default:
                throw new IllegalArgumentException("Unknown format: " + formatName);
        }

        System.out.println("Starting load test for format: " + format.getFormatName());
        runLoadTest(format);
    }

    private static void runLoadTest(SerializationFormat format) throws InterruptedException {
        // Метрики
        MetricsCollector.start();

        BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>(10000);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Продьюсеры – сериализуют и кладут в очередь
            for (int i = 0; i < CONCURRENCY; i++) {
                executor.submit(() -> {
                    try {
                        for (int j = 0; j < REQUESTS_PER_WORKER; j++) {
                            byte[] data = format.serialize(TEST_USER);
                            queue.put(data);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            // Консьюмеры – забирают из очереди и десериализуют
            for (int i = 0; i < CONCURRENCY; i++) {
                executor.submit(() -> {
                    try {
                        for (int j = 0; j < REQUESTS_PER_WORKER; j++) {
                            byte[] data = queue.take();
                            User user = (User) format.deserialize(data);
                            if (!TEST_USER.equals(user)) {
                                System.err.println("Data corruption detected!");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } // executor закрывается, ждём завершения всех виртуальных потоков

        MetricsCollector.stopAndPrint();
    }
}
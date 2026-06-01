package metrics

import (
	"fmt"
	"net/http"
	"runtime"
	"time"

	"github.com/prometheus/client_golang/prometheus"
	"github.com/prometheus/client_golang/prometheus/promauto"
	"github.com/prometheus/client_golang/prometheus/promhttp"
)

var (
	opsTotal = promauto.NewCounterVec(prometheus.CounterOpts{
		Name: "serialization_ops_total",
		Help: "Total number of serialization/deserialization operations",
	}, []string{"operation", "format"})

	latency = promauto.NewHistogramVec(prometheus.HistogramOpts{
		Name:    "serialization_duration_ns",
		Help:    "Latency of serialization/deserialization in nanoseconds",
		Buckets: prometheus.ExponentialBuckets(100, 2, 15),
	}, []string{"operation", "format"})

	memAlloc = promauto.NewGauge(prometheus.GaugeOpts{
		Name: "go_mem_alloc_bytes",
		Help: "Allocated heap bytes",
	})
	memTotalAlloc = promauto.NewGauge(prometheus.GaugeOpts{
		Name: "go_mem_total_alloc_bytes",
		Help: "Total allocated bytes (cumulative)",
	})
	numGoroutines = promauto.NewGauge(prometheus.GaugeOpts{
		Name: "go_goroutines",
		Help: "Number of goroutines",
	})
	gcPauseTotal = promauto.NewGauge(prometheus.GaugeOpts{
		Name: "go_gc_pause_total_ns",
		Help: "Total GC pause duration in nanoseconds",
	})
)

func StartMetricsServer() {
	http.Handle("/metrics", promhttp.Handler())
	go func() {
		if err := http.ListenAndServe(":9090", nil); err != nil {
			fmt.Printf("Metrics server error: %v\n", err)
		}
	}()
	fmt.Println("Prometheus metrics available at :9090/metrics")
}

func CollectRuntimeMetrics(interval time.Duration) {
	var memStats runtime.MemStats
	for range time.Tick(interval) {
		runtime.ReadMemStats(&memStats)
		memAlloc.Set(float64(memStats.Alloc))
		memTotalAlloc.Set(float64(memStats.TotalAlloc))
		numGoroutines.Set(float64(runtime.NumGoroutine()))
		gcPauseTotal.Set(float64(memStats.PauseTotalNs))
	}
}

package main

import "sync"

func RunLoadTest(format Format, concurrency int, requestsPerWorker int) {
	var wg sync.WaitGroup
	ch := make(chan []byte, 1000)

	// Воркеры-потребители
	for i := 0; i < concurrency; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			for data := range ch {
				_, _ = format.Deserialize(data)
			}
		}()
	}

	// Продюсеры
	for i := 0; i < concurrency; i++ {
		go func() {
			for j := 0; j < requestsPerWorker; j++ {
				data, _ := format.Serialize(user)
				ch <- data
			}
		}()
	}
	close(ch)
	wg.Wait()
}

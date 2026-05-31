go test -bench=. -count=10 -benchmem > bench.txt
benchstat bench.txt
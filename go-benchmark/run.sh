#!/bin/bash

set -e

# Установка зависимостей
deps() {
    go mod tidy
    # Установка benchstat (если не установлен)
    if ! command -v benchstat &> /dev/null; then
        echo "Installing benchstat..."
        go install golang.org/x/perf/cmd/benchstat@latest
    fi
}

# Генерация кода из protobuf и flatbuffers
generate() {
    echo "Generating protobuf code..."
    protoc --go_out=. user.proto
    echo "Generating flatbuffers code..."
    flatc --go -o . user.fbs
}

# Микробенчмарки (сравнение всех форматов)
bench() {
    echo "Running microbenchmarks..."
    go test -bench=. -count=10 -benchmem > bench.txt
    benchstat bench.txt
}

# Сборка (проверка компиляции)
build() {
    go build -o /dev/null ./...
}

# Очистка
clean() {
    rm -f bench.txt
    rm -f proto/*.pb.go
    rm -f flatbuf/*.go
}

case "$1" in
    deps)
        deps
        ;;
    generate)
        generate
        ;;
    bench)
        deps
        generate
        bench
        ;;
    build)
        deps
        generate
        build
        ;;
    clean)
        clean
        ;;
    *)
        echo "Usage: $0 {deps|generate|bench|loadtest <format>|build|clean}"
        exit 1
esac
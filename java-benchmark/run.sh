#!/bin/bash

# Запуск микробенчмарков JMH
run_jmh() {
    echo "Running JMH benchmarks..."
       java  -jar target/java-benchmark.jar ".*JMHSerializationBenchmark.*" -prof gc -rf json -rff jmh-result.json
}


# Сборка проекта
build() {
    mvn clean package
}

case "$1" in
    build)
        build
        ;;
    jmh)
        build
        run_jmh
        ;;
    *)
        echo "Usage: $0 {build|jmh <format>}"
        echo "Formats: json, xml, protobuf, flatbuffers"
        exit 1
esac
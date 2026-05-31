#!/bin/bash

# Запуск микробенчмарков JMH
run_jmh() {
    echo "Running JMH benchmarks..."
    java -javaagent:jmx_prometheus_javaagent-0.20.0.jar=8080:src/main/resources/prometheus-jmx-config.yaml \
         -jar target/java-benchmark.jar ".*JMHSerializationBenchmark.*" -rf json -rff jmh-result.json
}

# Запуск нагрузочного теста для указанного формата
run_loadtest() {
    FORMAT=$1
    echo "Running load test for format: $FORMAT"
    java -javaagent:jmx_prometheus_javaagent-0.20.0.jar=8080:src/main/resources/prometheus-jmx-config.yaml \
         -cp target/java-benchmark.jar bench.loadtest.LoadTest $FORMAT
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
    loadtest)
        build
        run_loadtest "$2"
        ;;
    *)
        echo "Usage: $0 {build|jmh|loadtest <format>}"
        echo "Formats: json, xml, protobuf, flatbuffers"
        exit 1
esac
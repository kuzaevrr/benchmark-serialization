# Исследовательский проект по изучению методов сериализации и дессериализации данных.

## [java-benchmark](java-benchmark)
Собирается командой 
```sh
cd java-benchmark && ./run.sh build && ../
```
Бенчмарк запускается командой:
```sh
cd java-benchmark && ./run.sh jmh && ../
```
Генерация flatbuffers:
```sh
cd java-benchmark && ./generate_flatbuffers.sh && ../
```
Генерация protobuf:
```sh
cd java-benchmark && ./generate_protobuf.sh && ../
```

## [go-benchmark](go-benchmark)
Собирается командой
```sh
cd go-benchmark && ./run.sh deps && ../
```
Бенчмарк запускается командой:
```sh
cd go-benchmark && ./run.sh bench && ../
```
Генерация flatbuffers:
```sh
cd go-benchmark && ./generate_flatbuffers.sh && ../
```
Генерация protobuf:
```sh
cd go-benchmark && ./generate_protobuf.sh && ../
```
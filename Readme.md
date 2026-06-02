# Исследовательский проект по изучению методов сериализации и дессериализации данных.

## [java-benchmark](java-benchmark)
Собирается командой 
```sh
cd java-benchmark && ./run.sh build && cd ../
```
Бенчмарк запускается командой:
```sh
cd java-benchmark && ./run.sh jmh && cd ../
```
Генерация flatbuffers:
```sh
cd java-benchmark && ./generate_flatbuffers.sh && cd ../
```
Генерация protobuf:
```sh
cd java-benchmark && ./generate_protobuf.sh && cd ../
```

## [go-benchmark](go-benchmark)
Собирается командой
```sh
cd go-benchmark && ./run.sh deps && cd ../
```
Бенчмарк запускается командой:
```sh
cd go-benchmark && ./run.sh bench && cd ../
```
Генерация flatbuffers:
```sh
cd go-benchmark && ./generate_flatbuffers.sh && cd ../
```
Генерация protobuf:
```sh
cd go-benchmark && ./generate_protobuf.sh && cd ../
```
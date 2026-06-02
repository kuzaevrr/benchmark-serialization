#!/bin/bash

# Генерация для Java
flatc --java -o src/main/java src/main/java/ram/ka/ru/user.fbs

echo "FlatBuffers code generation completed!"
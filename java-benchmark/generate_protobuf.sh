# Установка зависимостей для Java
mvn dependency:resolve

# Генерация Protobuf кода
protoc --java_out=src/main/java src/main/java/ram/ka/ru/user.proto
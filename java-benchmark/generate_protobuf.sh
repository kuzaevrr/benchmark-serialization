# Установка зависимостей для Java
mvn dependency:resolve

# Установка зависимостей для Go
#cd golang && go mod tidy && cd ../

# Генерация Protobuf кода
protoc --java_out=src/main/java src/main/java/ram/ka/ru/user.proto
#protoc --go_out=golang golang/user.proto
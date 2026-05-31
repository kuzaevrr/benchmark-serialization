package ram.ka.ru.model;

import java.util.Arrays;
import java.util.Objects;

public class User {
    private long id;
    private String name;
    private String email;
    private String[] tags;

    // Для FlatBuffers и Protobuf нужен конструктор по умолчанию
    public User() {
    }

    public User(long id, String name, String email, String[] tags) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tags = tags != null ? tags.clone() : new String[0];
    }

    // Геттеры и сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) && Arrays.equals(tags, user.tags);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, email);
        result = 31 * result + Arrays.hashCode(tags);
        return result;
    }

    // Создание тестового пользователя
    public static User createTestUser() {
        return new User(42L, "Alice Johnson", "alice@example.com",
                new String[]{"developer", "golang", "java"});
    }
}
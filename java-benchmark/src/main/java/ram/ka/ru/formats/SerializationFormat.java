package ram.ka.ru.formats;

public interface SerializationFormat {
    byte[] serialize(Object obj) throws Exception;
    Object deserialize(byte[] data) throws Exception;
    String getFormatName();
}
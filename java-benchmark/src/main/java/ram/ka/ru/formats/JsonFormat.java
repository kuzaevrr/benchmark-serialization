package ram.ka.ru.formats;

import com.fasterxml.jackson.databind.ObjectMapper;
import ram.ka.ru.model.User;

public class JsonFormat implements SerializationFormat {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) throws Exception {
        return mapper.writeValueAsBytes(obj);
    }

    @Override
    public User deserialize(byte[] data) throws Exception {
        return mapper.readValue(data, User.class);
    }

    @Override
    public String getFormatName() {
        return "JSON";
    }
}
package ram.ka.ru.formats;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ram.ka.ru.model.User;

public class XmlFormat implements SerializationFormat {
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public byte[] serialize(Object obj) throws Exception {
        return xmlMapper.writeValueAsBytes(obj);
    }

    @Override
    public User deserialize(byte[] data) throws Exception {
        return xmlMapper.readValue(data, User.class);
    }

    @Override
    public String getFormatName() {
        return "XML";
    }
}

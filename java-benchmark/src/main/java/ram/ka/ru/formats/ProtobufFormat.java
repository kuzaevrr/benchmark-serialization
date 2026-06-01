package ram.ka.ru.formats;

import ram.ka.ru.model.User;
import ram.ka.ru.model.UserProto;

public class ProtobufFormat implements SerializationFormat {
    @Override
    public byte[] serialize(Object obj) throws Exception {
        User user = (User) obj;
        UserProto.User.Builder builder = UserProto.User.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail());
        for (String tag : user.getTags()) {
            builder.addTags(tag);
        }
        return builder.build().toByteArray();
    }

    @Override
    public UserProto.User deserialize(byte[] data) throws Exception {
        return UserProto.User.parseFrom(data);
    }

    @Override
    public String getFormatName() {
        return "Protobuf";
    }
}
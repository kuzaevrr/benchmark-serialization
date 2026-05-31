package ram.ka.ru.formats;

import ram.ka.ru.model.User;

public class ProtobufFormat implements SerializationFormat {
    @Override
    public byte[] serialize(Object obj) throws Exception {
//        User user = (User) obj;
//        UserProto.User.Builder builder = UserProto.User.newBuilder()
//                .setId(user.getId())
//                .setName(user.getName())
//                .setEmail(user.getEmail());
//        for (String tag : user.getTags()) {
//            builder.addTags(tag);
//        }
//        return builder.build().toByteArray();
        return new byte[]{};
    }

    @Override
    public User deserialize(byte[] data) throws Exception {
//        UserProto.User protoUser = UserProto.User.parseFrom(data);
//        return new User(protoUser.getId(), protoUser.getName(),
//                protoUser.getEmail(),
//                protoUser.getTagsList().toArray(new String[0]));
        return new User();
    }

    @Override
    public String getFormatName() {
        return "Protobuf";
    }
}
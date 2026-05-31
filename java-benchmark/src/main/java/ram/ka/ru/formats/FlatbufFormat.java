package ram.ka.ru.formats;

import com.google.flatbuffers.FlatBufferBuilder;
import ram.ka.ru.model.User;

public class FlatbufFormat implements SerializationFormat {
    @Override
    public byte[] serialize(Object obj) throws Exception {
        User modelUser = (User) obj;
        FlatBufferBuilder builder = new FlatBufferBuilder();
//
//        int nameOffset = builder.createString(modelUser.getName());
//        int emailOffset = builder.createString(modelUser.getEmail());
//
//        int[] tagOffsets = new int[modelUser.getTags().length];
//        for (int i = 0; i < modelUser.getTags().length; i++) {
//            tagOffsets[i] = builder.createString(modelUser.getTags()[i]);
//        }
//        int tagsOffset = User.createTagsVector(builder, tagOffsets);
//
//        User.startUser(builder);
//        User.addId(builder, modelUser.getId());
//        User.addName(builder, nameOffset);
//        User.addEmail(builder, emailOffset);
//        User.addTags(builder, tagsOffset);
//        int userOffset = User.endUser(builder);
//        builder.finish(userOffset);

        return builder.sizedByteArray();
    }

    @Override
    public User deserialize(byte[] data) throws Exception {
//        ByteBuffer buf = ByteBuffer.wrap(data);
//        User flatUser = User.getRootAsUser(buf);
//        String[] tags = new String[flatUser.tagsLength()];
//        for (int i = 0; i < tags.length; i++) {
//            tags[i] = flatUser.tags(i);
//        }
//        return new bench.model.User(flatUser.id(), flatUser.name(),
//                flatUser.email(), tags);
        return new User();
    }

    @Override
    public String getFormatName() {
        return "FlatBuffers";
    }
}
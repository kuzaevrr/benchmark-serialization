package ram.ka.ru.formats;

import com.google.flatbuffers.FlatBufferBuilder;
import ram.ka.ru.model.User;
import ram.ka.ru.model.UserFlatbuf;

import java.nio.ByteBuffer;

public class FlatbufFormat implements SerializationFormat {

    @Override
    public byte[] serialize(Object obj) {
        User modelUser = (User) obj;
        FlatBufferBuilder builder = new FlatBufferBuilder();

        int nameOffset = builder.createString(modelUser.getName());
        int emailOffset = builder.createString(modelUser.getEmail());

        int[] tagOffsets = new int[modelUser.getTags().length];
        for (int i = 0; i < modelUser.getTags().length; i++) {
            tagOffsets[i] = builder.createString(modelUser.getTags()[i]);
        }
        int tagsOffset = UserFlatbuf.createTagsVector(builder, tagOffsets);

        UserFlatbuf.startUserFlatbuf(builder);
        UserFlatbuf.addId(builder, modelUser.getId());
        UserFlatbuf.addName(builder, nameOffset);
        UserFlatbuf.addEmail(builder, emailOffset);
        UserFlatbuf.addTags(builder, tagsOffset);

        builder.finish(
                UserFlatbuf.endUserFlatbuf(builder)
        );

        return builder.sizedByteArray();
    }

    @Override
    public UserFlatbuf deserialize(byte[] data) {
        ByteBuffer buf = ByteBuffer.wrap(data);
        return UserFlatbuf.getRootAsUserFlatbuf(buf);
    }

    @Override
    public String getFormatName() {
        return "FlatBuffers";
    }
}
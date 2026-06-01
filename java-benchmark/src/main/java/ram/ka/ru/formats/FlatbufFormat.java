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

        int[] tagOffsets = new int[modelUser.getTags().length];
        for (int i = 0; i < modelUser.getTags().length; i++) {
            tagOffsets[i] = builder.createString(modelUser.getTags()[i]);
        }

        UserFlatbuf.startUserFlatbuf(builder);
        UserFlatbuf.addId(builder, modelUser.getId());
        UserFlatbuf.addName(builder, builder.createString(modelUser.getName()));
        UserFlatbuf.addEmail(builder, builder.createString(modelUser.getEmail()));
        UserFlatbuf.addTags(builder, UserFlatbuf.createTagsVector(builder, tagOffsets));

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
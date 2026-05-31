package ram.ka.ru;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ram.ka.ru.formats.FlatbufFormat;
import ram.ka.ru.formats.JsonFormat;
import ram.ka.ru.formats.ProtobufFormat;
import ram.ka.ru.formats.XmlFormat;
import ram.ka.ru.model.User;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class JMHSerializationBenchmark {

    private User testUser;
    private JsonFormat jsonFormat;
    private XmlFormat xmlFormat;
    private ProtobufFormat protobufFormat;
    private FlatbufFormat flatbufFormat;

    private byte[] jsonData;
    private byte[] xmlData;
    private byte[] protoData;
    private byte[] flatbufData;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        testUser = User.createTestUser();
        jsonFormat = new JsonFormat();
        xmlFormat = new XmlFormat();
        protobufFormat = new ProtobufFormat();
        flatbufFormat = new FlatbufFormat();

        jsonData = jsonFormat.serialize(testUser);
        xmlData = xmlFormat.serialize(testUser);
        protoData = protobufFormat.serialize(testUser);
        flatbufData = flatbufFormat.serialize(testUser);
    }

    // ------------------- Сериализация -------------------
    @Benchmark
    public byte[] serializeJson() throws Exception {
        return jsonFormat.serialize(testUser);
    }

    @Benchmark
    public byte[] serializeXml() throws Exception {
        return xmlFormat.serialize(testUser);
    }

    @Benchmark
    public byte[] serializeProtobuf() throws Exception {
        return protobufFormat.serialize(testUser);
    }

    @Benchmark
    public byte[] serializeFlatbuffers() throws Exception {
        return flatbufFormat.serialize(testUser);
    }

    // ------------------- Десериализация -------------------
    @Benchmark
    public User deserializeJson(Blackhole bh) throws Exception {
        return jsonFormat.deserialize(jsonData);
    }

    @Benchmark
    public User deserializeXml(Blackhole bh) throws Exception {
        return xmlFormat.deserialize(xmlData);
    }

    @Benchmark
    public User deserializeProtobuf(Blackhole bh) throws Exception {
        return protobufFormat.deserialize(protoData);
    }

    @Benchmark
    public User deserializeFlatbuffers(Blackhole bh) throws Exception {
        return flatbufFormat.deserialize(flatbufData);
    }

    // ------------------- Полный цикл (сериализация + десериализация) -------------------
    @Benchmark
    public User fullCycleJson(Blackhole bh) throws Exception {
        byte[] data = jsonFormat.serialize(testUser);
        return jsonFormat.deserialize(data);
    }

    @Benchmark
    public User fullCycleXml(Blackhole bh) throws Exception {
        byte[] data = xmlFormat.serialize(testUser);
        return xmlFormat.deserialize(data);
    }

    @Benchmark
    public User fullCycleProtobuf(Blackhole bh) throws Exception {
        byte[] data = protobufFormat.serialize(testUser);
        return protobufFormat.deserialize(data);
    }

    @Benchmark
    public User fullCycleFlatbuffers(Blackhole bh) throws Exception {
        byte[] data = flatbufFormat.serialize(testUser);
        return flatbufFormat.deserialize(data);
    }
}
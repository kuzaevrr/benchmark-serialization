package ram.ka.ru;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ram.ka.ru.formats.FlatbufFormat;
import ram.ka.ru.formats.JsonFormat;
import ram.ka.ru.formats.ProtobufFormat;
import ram.ka.ru.formats.XmlFormat;
import ram.ka.ru.model.User;
import ram.ka.ru.model.UserFlatbuf;
import ram.ka.ru.model.UserProto;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
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
    public UserProto.User deserializeProtobuf(Blackhole bh) throws Exception {
        return protobufFormat.deserialize(protoData);
    }

    @Benchmark
    public UserFlatbuf deserializeFlatbuffers(Blackhole bh) throws Exception {
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
    public UserProto.User fullCycleProtobuf(Blackhole bh) throws Exception {
        byte[] data = protobufFormat.serialize(testUser);
        return protobufFormat.deserialize(data);
    }

    @Benchmark
    public UserFlatbuf fullCycleFlatbuffers(Blackhole bh) throws Exception {
        byte[] data = flatbufFormat.serialize(testUser);
        return flatbufFormat.deserialize(data);
    }

    // =================== НОВЫЕ БЕНЧМАРКИ ДЛЯ ПИКОВОЙ OPS НА 1 ЯДРО ===================
    // Измеряем пропускную способность (операций в секунду) строго в одном потоке (1 ядро)

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public byte[] serializeJsonThroughput() throws Exception {
        return jsonFormat.serialize(testUser);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public byte[] serializeXmlThroughput() throws Exception {
        return xmlFormat.serialize(testUser);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public byte[] serializeProtobufThroughput() throws Exception {
        return protobufFormat.serialize(testUser);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public byte[] serializeFlatbuffersThroughput() throws Exception {
        return flatbufFormat.serialize(testUser);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public User deserializeJsonThroughput(Blackhole bh) throws Exception {
        return jsonFormat.deserialize(jsonData);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public User deserializeXmlThroughput(Blackhole bh) throws Exception {
        return xmlFormat.deserialize(xmlData);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public UserProto.User deserializeProtobufThroughput(Blackhole bh) throws Exception {
        return protobufFormat.deserialize(protoData);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public UserFlatbuf deserializeFlatbuffersThroughput(Blackhole bh) throws Exception {
        return flatbufFormat.deserialize(flatbufData);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public User fullCycleJsonThroughput(Blackhole bh) throws Exception {
        byte[] data = jsonFormat.serialize(testUser);
        return jsonFormat.deserialize(data);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public User fullCycleXmlThroughput(Blackhole bh) throws Exception {
        byte[] data = xmlFormat.serialize(testUser);
        return xmlFormat.deserialize(data);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public UserProto.User fullCycleProtobufThroughput(Blackhole bh) throws Exception {
        byte[] data = protobufFormat.serialize(testUser);
        return protobufFormat.deserialize(data);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(1)
    public UserFlatbuf fullCycleFlatbuffersThroughput(Blackhole bh) throws Exception {
        byte[] data = flatbufFormat.serialize(testUser);
        return flatbufFormat.deserialize(data);
    }
}
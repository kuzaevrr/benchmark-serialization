package main

import (
	"go-benchmark/format"
	"go-benchmark/model"
	"testing"
)

var (
	testUserModel = model.NewTestUser()

	testUserProto = &model.UserProto{
		Id:    testUserModel.ID,
		Name:  testUserModel.Name,
		Email: testUserModel.Email,
		Tags:  testUserModel.Tags,
	}

	jsonFormat     = &format.JsonFormat{}
	xmlFormat      = &format.XmlFormat{}
	protobufFormat = &format.ProtobufFormat{}
	flatbufFormat  = &format.FlatbufFormat{}

	// предварительно сериализованные данные
	jsonData, _    = jsonFormat.Serialize(testUserModel)
	xmlData, _     = xmlFormat.Serialize(testUserModel)
	protoData, _   = protobufFormat.Serialize(testUserProto)
	flatbufData, _ = flatbufFormat.Serialize(testUserModel)
)

// ---------- Сериализация ----------
func BenchmarkSerializeJSON(b *testing.B) {
	for i := 0; i < b.N; i++ {
		_, _ = jsonFormat.Serialize(testUserModel)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkSerializeXML(b *testing.B) {
	for i := 0; i < b.N; i++ {
		_, _ = xmlFormat.Serialize(testUserModel)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkSerializeProtobuf(b *testing.B) {
	for i := 0; i < b.N; i++ {
		_, _ = protobufFormat.Serialize(testUserProto)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkSerializeFlatbuffers(b *testing.B) {
	for i := 0; i < b.N; i++ {
		_, _ = flatbufFormat.Serialize(testUserModel)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

// ---------- Десериализация ----------
func BenchmarkDeserializeJSON(b *testing.B) {
	var user model.User
	for i := 0; i < b.N; i++ {
		_ = jsonFormat.Deserialize(jsonData, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkDeserializeXML(b *testing.B) {
	var user model.User
	for i := 0; i < b.N; i++ {
		_ = xmlFormat.Deserialize(xmlData, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkDeserializeProtobuf(b *testing.B) {
	var user model.UserProto
	for i := 0; i < b.N; i++ {
		_ = protobufFormat.Deserialize(protoData, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkDeserializeFlatbuffers(b *testing.B) {
	var user model.UserFlatbuf
	for i := 0; i < b.N; i++ {
		_ = flatbufFormat.Deserialize(flatbufData, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

// ---------- Полный цикл (Serialize + Deserialize) ----------
func BenchmarkFullCycleJSON(b *testing.B) {
	for i := 0; i < b.N; i++ {
		data, _ := jsonFormat.Serialize(testUserModel)
		var user model.User
		_ = jsonFormat.Deserialize(data, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkFullCycleXML(b *testing.B) {
	for i := 0; i < b.N; i++ {
		data, _ := xmlFormat.Serialize(testUserModel)
		var user model.User
		_ = xmlFormat.Deserialize(data, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkFullCycleProtobuf(b *testing.B) {
	for i := 0; i < b.N; i++ {
		data, _ := protobufFormat.Serialize(testUserProto)
		var user model.UserProto
		_ = protobufFormat.Deserialize(data, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

func BenchmarkFullCycleFlatbuffers(b *testing.B) {
	for i := 0; i < b.N; i++ {
		data, _ := flatbufFormat.Serialize(testUserModel)
		var user model.UserFlatbuf
		_ = flatbufFormat.Deserialize(data, &user)
	}
	b.ReportMetric(float64(b.N)/b.Elapsed().Seconds(), "ops/s")
}

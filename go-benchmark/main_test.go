package main

import (
	"benchmark/formats"
	"testing"
)

var user = &formats.User{Id: 1, Name: "Alice", Email: "alice@ex.com"}

func BenchmarkSerializeJSON(b *testing.B) {
	f := &formats.JsonFormat{}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		_, _ = f.Serialize(user)
	}
}

func BenchmarkDeserializeJSON(b *testing.B) {
	f := &formats.JsonFormat{}
	data, _ := f.Serialize(user)
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		_, _ = f.Deserialize(data)
	}
}

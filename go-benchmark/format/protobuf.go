package format

import (
	"go-benchmark/model"
	"google.golang.org/protobuf/proto"
)

type ProtobufFormat struct{}

func (f *ProtobufFormat) Serialize(v interface{}) ([]byte, error) {
	user := v.(*model.UserProto)
	return proto.Marshal(user)
}

func (f *ProtobufFormat) Deserialize(data []byte, v interface{}) error {
	user := v.(*model.UserProto)
	return proto.Unmarshal(data, user)
}

func (f *ProtobufFormat) Name() string {
	return "Protobuf"
}

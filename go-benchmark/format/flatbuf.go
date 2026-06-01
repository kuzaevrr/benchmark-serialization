package format

import (
	"github.com/google/flatbuffers/go"
	"go-benchmark/model"
)

type FlatbufFormat struct{}

func (f *FlatbufFormat) Serialize(v interface{}) ([]byte, error) {
	userModel := v.(*model.User) // наша модель
	builder := flatbuffers.NewBuilder(1024)

	nameOffset := builder.CreateString(userModel.Name)
	emailOffset := builder.CreateString(userModel.Email)

	tagOffsets := make([]flatbuffers.UOffsetT, len(userModel.Tags))
	for i, tag := range userModel.Tags {
		tagOffsets[i] = builder.CreateString(tag)
	}
	model.UserFlatbufStartTagsVector(builder, len(tagOffsets))
	for i := len(tagOffsets) - 1; i >= 0; i-- {
		builder.PrependUOffsetT(tagOffsets[i])
	}
	tagsOffset := builder.EndVector(len(tagOffsets))

	model.UserFlatbufStart(builder)
	model.UserFlatbufAddId(builder, userModel.ID)
	model.UserFlatbufAddName(builder, nameOffset)
	model.UserFlatbufAddEmail(builder, emailOffset)
	model.UserFlatbufAddTags(builder, tagsOffset)
	userOffset := model.UserFlatbufEnd(builder)

	builder.Finish(userOffset)
	return builder.FinishedBytes(), nil
}

func (f *FlatbufFormat) Deserialize(data []byte, v interface{}) error {
	userFB := model.GetRootAsUserFlatbuf(data, 0)
	v = userFB

	return nil
}

func (f *FlatbufFormat) Name() string {
	return "FlatBuffers"
}

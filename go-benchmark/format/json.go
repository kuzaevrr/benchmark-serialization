package format

import (
	"encoding/json"
)

type JsonFormat struct{}

func (f *JsonFormat) Serialize(v interface{}) ([]byte, error) {
	return json.Marshal(v)
}

func (f *JsonFormat) Deserialize(data []byte, v interface{}) error {
	return json.Unmarshal(data, v)
}

func (f *JsonFormat) Name() string {
	return "JSON"
}

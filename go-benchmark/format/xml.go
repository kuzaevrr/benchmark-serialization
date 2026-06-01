package format

import (
	"encoding/xml"
)

type XmlFormat struct{}

func (f *XmlFormat) Serialize(v interface{}) ([]byte, error) {
	return xml.Marshal(v)
}

func (f *XmlFormat) Deserialize(data []byte, v interface{}) error {
	return xml.Unmarshal(data, v)
}

func (f *XmlFormat) Name() string {
	return "XML"
}

package format

type SerializationFormat interface {
	Serialize(v interface{}) ([]byte, error)
	Deserialize(data []byte, v interface{}) error
	Name() string
}

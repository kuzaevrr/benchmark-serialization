package model

type User struct {
	ID    int64    `json:"id" xml:"id"`
	Name  string   `json:"name" xml:"name"`
	Email string   `json:"email" xml:"email"`
	Tags  []string `json:"tags" xml:"tags"`
}

// NewTestUser создаёт тестового пользователя
func NewTestUser() *User {
	return &User{
		ID:    42,
		Name:  "Alice Johnson",
		Email: "alice@example.com",
		Tags:  []string{"developer", "golang", "java"},
	}
}

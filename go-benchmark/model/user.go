package model

type User struct {
	ID    int64
	Name  string
	Email string
	Tags  []string
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

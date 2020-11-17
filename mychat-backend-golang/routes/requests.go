package routes

type LoginRequest struct {
	account  string
	password string
}

type RegisterRequest struct {
	account  string
	nick     string
	password string
}

type PostAddRequest struct {
	title string
}

type CommentAddRequest struct {
	commentID int64
	content   string
}

type CommentsGetAll struct {
	postID int64
}

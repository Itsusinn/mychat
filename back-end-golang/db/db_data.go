package db

type User struct {
	UID      int64  `xorm:"pk int notnull autoincr"`
	Account  string `xorm:"string notnull unique"`
	Password string `xorm:"string notnull"`
	Nick     string `xorm:"string notnull"`
}
type Comment struct {
	CommentID int64  `xorm:"pk int notnull autoincr"`
	SubjectID int64  `xorm:"int notnull"`
	AuthorID  int64  `xorm:"int notnull"`
	Content   string `xorm:"text notnull"`
}
type Post struct {
	PostID   int64  `xorm:"pk int notnull autoincr"`
	Title    string `xorm:"notnull"`
	AuthorID int64  `xorm:"int"`
}

/**
CREATE TABLE comments(
    comment_id int not null primary key auto_increment,
    subject int not null,
    author int not null,
    content text(500) not null
);
CREATE TABLE users(
    uid int not null primary key auto_increment,
    account varchar(40) not null,
    nick varchar(40) not null,
    password varchar(40) not null
);
CREATE TABLE posts(
    post_id int not null primary key auto_increment,
    title varchar(40) not null,
    author int not null
);
*/

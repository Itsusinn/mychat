package service

import (
	log "github.com/sirupsen/logrus"
	. "mychat/db"
)

var post = &Post{}

func AddPost(authorID int64, title string) (Post, error) {

	newPost := Post{Title: title, AuthorID: authorID}

	_, err := Database.Insert(&newPost)
	if err != nil {
		log.WithField("Post", newPost).Error("Post Add Failed")
		return Post{}, err
	}

	return newPost, nil
}

/**
return posts after [time] after
*/
func GetAllPosts(after int64) ([]Post, error) {

	var posts []Post

	err := Database.
		Where("create_at >= ?", after).
		Desc("create_at").
		Find(&posts, post)

	if err != nil {
		log.Error("Get all posts failed")
		return nil, err
	}

	return posts, nil
}

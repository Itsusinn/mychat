package service

import (
	log "github.com/sirupsen/logrus"
	. "mychat/db"
)

func AddComment(authorID int64, postID int64, content string) (Comment, error) {
	newComment := Comment{AuthorID: authorID, PostID: postID, Content: content}
	_, err := Database.Insert(&newComment)
	if err != nil {
		log.WithField("Comment", newComment).Debug("Comment add Failed")
		return Comment{}, nil
	}
	return newComment, nil
}

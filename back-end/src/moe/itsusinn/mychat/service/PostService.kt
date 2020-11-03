package moe.itsusinn.mychat.service

import moe.itsusinn.mychat.CommentAddEvent
import moe.itsusinn.mychat.sql.comments
import moe.itsusinn.mychat.sql.data.Comment
import moe.itsusinn.mychat.sql.data.Post
import moe.itsusinn.mychat.sql.database
import moe.itsusinn.mychat.sql.posts
import org.ktorm.entity.add
import org.ktorm.entity.toList

object PostService {
    fun addPost(author:Int,title:String):Post{
        val newPost = Post{
            this.author = author
            this.title = title
        }
        database.posts.add(newPost)
        return newPost
    }

    fun addComment(author: Int,content:String,subject:Int): Comment {
        val newComment = Comment{
            uid = author
            this.content = content
            this.subject = subject
        }
        database.comments.add(newComment)
        return newComment
    }

    fun getAllPosts(): List<Post> {
        return database.posts.toList()
    }
}
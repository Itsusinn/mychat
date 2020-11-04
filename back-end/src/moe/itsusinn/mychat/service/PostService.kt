package moe.itsusinn.mychat.service

import moe.itsusinn.mychat.sql.comments
import moe.itsusinn.mychat.sql.data.*
import moe.itsusinn.mychat.sql.database
import moe.itsusinn.mychat.sql.posts
import org.ktorm.dsl.*
import org.ktorm.entity.add

/**
 * separate the sql operation and main logic
 */
object PostService {
    //add a new post
    fun addPost(author:Int,title:String):Post{
        val newPost = Post{
            this.author = author
            this.title = title
        }
        database.posts.add(newPost)
        return newPost
    }
    //add a new comment
    fun addComment(author: Int,content:String,subject:Int): Comment {
        val newComment = Comment{
            this.author = author
            this.content = content
            this.subject = subject
        }
        database.comments.add(newComment)
        return newComment
    }
    //get all comments
    fun getComments(subject: Int):List<CommentData>{
        return database.from(Comments)
            .select()
            .where { Comments.id eq subject }
            .map {row ->
                val id = row[Comments.id] ?: 0
                val author = row[Comments.author] ?: 0
                val content = row[Comments.content] ?: ""
                CommentData(id, author, content)
            }
    }
    //get all posts
    fun getAllPosts(): List<PostData> {
        return database.from(Posts)
            .select()
            .map {row ->
                val author = row[Posts.author] ?: 0
                val title = row[Posts.title] ?: ""
                val id = row[Posts.id] ?: 0
                PostData(id,author,title)
            }
    }
}
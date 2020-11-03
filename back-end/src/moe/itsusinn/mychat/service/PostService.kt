package moe.itsusinn.mychat.service

import moe.itsusinn.mychat.data.PostData
import moe.itsusinn.mychat.sql.comments
import moe.itsusinn.mychat.sql.data.Comment
import moe.itsusinn.mychat.sql.data.Post
import moe.itsusinn.mychat.sql.data.Posts
import moe.itsusinn.mychat.sql.database
import moe.itsusinn.mychat.sql.posts
import org.ktorm.dsl.forEach
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.entity.add

/**
 * separate the sql operation and main logic
 */
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

    fun getAllPosts(): List<PostData> {
        val result = mutableListOf<PostData>()
        database.from(Posts)
            .select()
            .forEach {row ->
                val author = row[Posts.author] ?: 0
                val title = row[Posts.title] ?: ""
                val id = row[Posts.id] ?: 0
                result.add(PostData(id,author,title))
            }
        return result
    }
}
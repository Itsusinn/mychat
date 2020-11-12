package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.models.CommentData
import moe.itsusinn.mychat.models.PostData
import moe.itsusinn.mychat.repository.entity.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.springframework.stereotype.Service

@Service
class PostService(
    val database: Database
) {
    /**
     * Add a new Post
     */
    fun addPost(author:Long,title:String): Post {
        val newPost = Post{
            this.authorID = authorID
            this.title = title
        }
        database.posts.add(newPost)
        return newPost
    }
    //add a new comment
    fun addComment(authorID: Long,content:String,subjectID:Long): Comment {
        val newComment = Comment{
            this.authorID = authorID
            this.content = content
            this.subjectID = subjectID
        }
        database.comments.add(newComment)
        return newComment
    }
    //get all comments
    fun getComments(subjectID: Long):List<CommentData>{
        return database.from(Comments)
            .select()
            .where { Comments.commentID eq subjectID }
            .map {row ->
                val id = row[Comments.commentID] ?: 0
                val author = row[Comments.authorID] ?: 0
                val content = row[Comments.content] ?: ""
                CommentData(id, author, content)
            }
    }
    //get all posts
    fun getAllPosts(): List<PostData> {
        return database.from(Posts)
            .select()
            .map {row ->
                val author = row[Posts.authorID] ?: 0
                val title = row[Posts.title] ?: ""
                val id = row[Posts.postID] ?: 0
                PostData(id,author,title)
            }
    }
}
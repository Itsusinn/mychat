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
    fun addPost(author: Long, title: String): PostEntity {
        val newPost = PostEntity {
            this.authorID = authorID
            this.title = title
        }
        database.posts.add(newPost)
        return newPost
    }

    //add a new comment
    fun addComment(authorID: Long, content: String, postID: Long): CommentEntity {
        val newComment = CommentEntity {
            this.authorID = authorID
            this.content = content
            this.postID = postID
        }
        database.comments.add(newComment)
        return newComment
    }

    //get all comments
    fun getComments(postID: Long): List<CommentData> {
        val comments = mutableListOf<CommentData>()
        database.from(CommentTable)
            .select()
            .where { CommentTable.postID eq postID }
            .forEach { row ->
                val commentID = row[CommentTable.commentID]
                    ?: return@forEach
                val authorID = row[CommentTable.authorID]
                    ?: return@forEach
                val content = row[CommentTable.content]
                    ?: return@forEach
                comments.add(CommentData(postID, commentID, authorID, content))
            }
        return comments
    }
    //get all posts
    fun getAllPosts(): List<PostData> {
        return database.from(PostTable)
            .select()
            .map { row ->
                val author = row[PostTable.authorID] ?: 0
                val title = row[PostTable.title] ?: ""
                val id = row[PostTable.postID] ?: 0
                PostData(id, author, title)
            }
    }
}
package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.text

interface CommentEntity : Entity<CommentEntity> {
    companion object : Entity.Factory<CommentEntity>()

    var commentID: Long
    var userID: Long
    var postID: Long
    var content: String
}

object CommentTable : Table<CommentEntity>("comment") {
    val commentID = long("comment_id").primaryKey().bindTo { it.commentID }
    val userID = long("user_id").bindTo { it.userID }
    val postID = long("post_id").bindTo { it.postID }
    val content = text("content").bindTo { it.content }
}

val Database.comments get() = this.sequenceOf(CommentTable)
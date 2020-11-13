package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.text

interface Comment : Entity<Comment> {
    companion object : Entity.Factory<Comment>()
    var commentID: Long
    var authorID: Long
    var postID: Long
    var content: String
}

object Comments : Table<Comment>("comment") {
    val commentID = long("comment_id").primaryKey().bindTo { it.commentID }
    val authorID = long("author_id").bindTo { it.authorID }
    val postID = long("post_id").bindTo { it.postID }
    val content = text("content").bindTo { it.content }
}

val Database.comments get() = this.sequenceOf(Comments)
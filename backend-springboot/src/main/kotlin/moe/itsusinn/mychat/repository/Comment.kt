package moe.itsusinn.mychat.repository

import org.ktorm.database.Database
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.long

interface Comment : Entity<Comment> {
    companion object : Entity.Factory<Comment>()
    var commentID: Long
    var authorID: Long
    var subjectID: Long
    var content: String
}

object Comments:Table<Comment>("comments"){
    val commentID = long("comment_id").primaryKey().bindTo { it.commentID }
    val authorID = long("author_id").bindTo { it.authorID }
    val subjectID = long("subject_id").bindTo { it.subjectID }
    val content = text("content").bindTo { it.content }
}

val Database.comments get() = this.sequenceOf(Comments)
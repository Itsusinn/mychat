package moe.itsusinn.mychat.sql.data

import moe.itsusinn.mychat.sql.UID
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.entity.Entity

interface Comment : Entity<Comment> {
    companion object : Entity.Factory<Comment>()
    var comment_id: Int
    var author:UID
    var subject: Int
    var content:String
}

data class CommentData(val id: Int,val author:Int,val content:String)

object Comments:Table<Comment>("comments"){
    val comment_id = int("comment_id").primaryKey().bindTo { it.comment_id }
    val author = int("author").bindTo { it.author }
    val subject = int("subject").bindTo { it.subject }
    val content = text("content").bindTo { it.content }
}
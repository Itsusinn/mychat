package org.meowcat.sqldata

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar
import org.ktorm.entity.Entity

interface Comment : Entity<Comment> {
    companion object : Entity.Factory<Comment>()
    var id: Int?
    var subject: Int
    var nick:String
    var email:String
    var content:String
}

object Comments:Table<Comment>("comments"){
    val id = int("id").primaryKey().bindTo { it.id }
    val subject = int("subject").bindTo { it.subject }
    val nick = varchar("nick").bindTo { it.nick}
    val email = varchar("email").bindTo { it.email }
    val content = text("content").bindTo { it.content }
}
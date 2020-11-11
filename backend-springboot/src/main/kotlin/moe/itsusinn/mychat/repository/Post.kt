package moe.itsusinn.mychat.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface Post : Entity<Post> {
    companion object : Entity.Factory<Post>()
    var postID: Long
    var authorID: Long
    var title:String
}

object Posts: Table<Post>("posts"){
    val postID = long("id").primaryKey().bindTo { it.postID }
    val authorID = long("author_id").bindTo { it.authorID }
    val title = varchar("title").bindTo { it.title }
}

val Database.posts get() = this.sequenceOf(Posts)
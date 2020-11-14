package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface PostEntity : Entity<PostEntity> {
    companion object : Entity.Factory<PostEntity>()
    var postID: Long
    var userID: Long
    var title: String
}

object PostTable : Table<PostEntity>("post") {
    val postID = long("post_id").primaryKey().bindTo { it.postID }
    val userID = long("user_id").bindTo { it.userID }
    val title = varchar("title").bindTo { it.title }
}

val Database.posts get() = this.sequenceOf(PostTable)
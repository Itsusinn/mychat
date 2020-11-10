package moe.itsusinn.mychat.sql.data

import moe.itsusinn.mychat.sql.UID
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface Post : Entity<Post> {
    companion object : Entity.Factory<Post>()
    var post_id: Int
    var author:UID
    var title:String
}

data class PostData(val id:Int, val uid:Int, val title:String)

object Posts: Table<Post>("posts"){
    val post_id = int("id").primaryKey().bindTo { it.post_id }
    val author = int("author").bindTo { it.author }
    val title = varchar("title").bindTo { it.title }
}
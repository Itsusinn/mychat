package moe.itsusinn.mychat.sql.data

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface Post : Entity<Post> {
    companion object : Entity.Factory<Post>()
    var id: Int
    var author:Int
    var title:String
}

object Posts: Table<Post>("posts"){
    val id = int("id").primaryKey().bindTo { it.id }
    val author = int("author").bindTo { it.author }
    val title = varchar("title").bindTo { it.title }
}
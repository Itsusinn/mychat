package moe.itsusinn.mychat.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface User : Entity<User> {
    companion object : Entity.Factory<User>()
    var uid: Long
    var username:String
    var nick:String
    var password:String
}

object Users: Table<User>("users"){
    val uid = long("uid").primaryKey().bindTo { it.uid }
    val username = varchar("username").bindTo { it.username }
    val nick = varchar("nick").bindTo { it.nick}
    val password = varchar("password").bindTo { it.password }
}

val Database.users get() = this.sequenceOf(Users)

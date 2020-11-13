package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface UserEntity : Entity<UserEntity> {
    companion object : Entity.Factory<UserEntity>()

    var uid: Long
    var username: String
    var nick: String
    var password: String
}

object UserTable : Table<UserEntity>("user") {
    val uid = long("uid").primaryKey().bindTo { it.uid }
    val username = varchar("username").bindTo { it.username }
    val nick = varchar("nick").bindTo { it.nick }
    val password = varchar("password").bindTo { it.password }
}

val Database.users get() = this.sequenceOf(UserTable)

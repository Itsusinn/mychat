package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface UserEntity : Entity<UserEntity> {
    companion object : Entity.Factory<UserEntity>()

    var userID: Long
    var username: String
    var password: String
}

object UserTable : Table<UserEntity>("user") {
    val userID = long("user_id").primaryKey().bindTo { it.userID }
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
}

val Database.users get() = this.sequenceOf(UserTable)

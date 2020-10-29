package moe.itsusinn.mychat.sql.data

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar


interface User : Entity<User> {
    companion object : Entity.Factory<User>()
    var uid: Int
    var account:String
    var nick:String
    var password:String
}

object Users: Table<User>("users"){
    val uid = int("id").primaryKey().bindTo { it.uid }
    val account = varchar("account").bindTo { it.account }
    val nick = varchar("nick").bindTo { it.nick}
    val password = varchar("password").bindTo { it.password }
}
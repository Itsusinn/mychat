package moe.itsusinn.mychat.service

import io.ktor.application.*
import moe.itsusinn.mychat.extend.err
import moe.itsusinn.mychat.sql.data.User
import moe.itsusinn.mychat.sql.database
import moe.itsusinn.mychat.sql.users
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find

object UserService {
    fun findUserByAccount(account:String): User?{
        return database.users.find { it.account eq account }
    }
    fun findUserByUid(uid:Int): User?{
        return database.users.find { it.uid eq uid }
    }
    suspend fun addUser(account: String, nick:String, password:String): User? {
        val repeat = findUserByAccount(account)
        if (repeat!=null) return null
        val newUser = User{
            this.account = account
            this.nick = nick
            this.password = password
        }
        database.users.add(newUser)
        return newUser
    }
}
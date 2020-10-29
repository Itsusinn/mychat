package moe.itsusinn.mychat.service

import moe.itsusinn.mychat.sql.data.User
import moe.itsusinn.mychat.sql.database
import moe.itsusinn.mychat.sql.users
import org.ktorm.dsl.eq
import org.ktorm.entity.find

object UserService {
    fun findUserByAccount(account:String): User?{
        return database.users.find { it.account eq account }
    }
    fun findUserByUid(uid:Int): User?{
        return database.users.find { it.uid eq uid }
    }
    fun addUser(){

    }
}
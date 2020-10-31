package moe.itsusinn.mychat.service

import moe.itsusinn.mychat.sql.data.User
import moe.itsusinn.mychat.sql.database
import moe.itsusinn.mychat.sql.users
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find

object SqlService {
    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByAccount(account:String): User?{
        return database.users.find { it.account eq account }
    }
    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByUid(uid:Int): User?{
        return database.users.find { it.uid eq uid }
    }
    /**
     * 注册新用户,不检查是否已经注册
     */
    fun addUser(account: String, nick:String, password:String):User?{
        val newUser = User{
            this.account = account
            this.nick = nick
            this.password = password
        }
        if (newUser.uid!=0) error("When add a user, its uid should be blank")
        database.users.add(newUser)
        return newUser
    }
}
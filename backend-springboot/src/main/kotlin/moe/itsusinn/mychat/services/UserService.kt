package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.User
import moe.itsusinn.mychat.repository.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.springframework.stereotype.Service

@Service
class UserService(
    val database: Database
) {

    /**
     * 校检密码是否正确
     */
    fun checkPassword(account: String,password:String): User?{
        val user = findUserByAccount(account)
        return if (user==null||user.password!=password) null else user
    }

    /**
     * 添加一个新用户
     * 有重复account则返回null
     */
    fun addNewUser(account: String, nick:String, password:String): User? {
        val repeat = findUserByAccount(account)
        //如果有重复的则返回null
        if (repeat!=null) return null
        val newUser = User{
            this.username = account
            this.nick = nick
            this.password = password
        }
        if (newUser.uid!=0L) error("When add a user, its uid should be blank")
        database.users.add(newUser)
        return newUser
    }

    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByAccount(account:String): User?
            = database.users.find { it.username eq account }

    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByUid(uid:Long): User?
            = database.users.find { it.uid eq uid }

//    /**
//     * 维持一个用户的登录态
//     */
//    fun login(account: String,password:String): UidPrincipal? {
//        val user = checkPassword(account, password) ?: return null
//        val uuid = UUID.randomUUID().toString()
//        RedisService.login(user.uid, uuid)
//        return UidPrincipal(user.uid, uuid)
//    }
//
//    /**
//     * 该方法应该在持有jwt token的情况下调用
//     */
//    fun logout(uid: Int,uuid:String){
//        RedisService.logout(uid, uuid)
//    }
//    /**
//     * 该方法应该在持有jwt token的情况下调用
//     */
//    fun logoutAll(uid: Int){
//        RedisService.allLogout(uid)
//    }
//    /**
//     * 该方法应该在持有jwt token的情况下调用
//     */
//    fun changePassword(uid: Int,newPassword:String){
//        val user = findUserByAccount(uid.toString()) ?: return
//        user.password = newPassword
//        user.flushChanges()
//        RedisService.allLogout(uid)
//    }



}
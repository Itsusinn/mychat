package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.UserRepository
import moe.itsusinn.mychat.repository.entity.User
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    /**
     * 校检密码是否正确
     */
    fun checkPassword(account: String,password:String): User?{
        val user = userRepository.findUserByAccount(account)
        return if (user==null||user.password!=password) null else user
    }

    /**
     * 添加一个新用户
     * 有重复account则返回null
     */
    fun addNewUser(account: String, nick:String, password:String): User? {
        val repeat = userRepository.findUserByAccount(account)
        //如果有重复的则返回null
        if (repeat!=null) return null
        val newUser = User{
            this.username = account
            this.nick = nick
            this.password = password
        }
        userRepository.add(newUser)
        return newUser
    }

}
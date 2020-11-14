package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.UserRepository
import moe.itsusinn.mychat.repository.entity.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    /**
     * 校检密码是否正确
     */
    fun checkPassword(account: String, password: String): UserEntity? {
        val user = userRepository.findUserByAccount(account)
        return if (user == null || user.password != password) null else user
    }

    /**
     * 添加一个新用户
     * 有重复account则返回null
     */
    fun registerUser(account: String, nick: String, password: String): UserEntity? {
        val repeat = userRepository.findUserByAccount(account)
        //如果有重复的则返回null
        if (repeat != null) return null
        val newUser = UserEntity {
            this.username = account
            this.nick = nick
            this.password = password
        }
        userRepository.save(newUser)
        return newUser
    }

}
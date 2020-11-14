package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.UserRepository
import moe.itsusinn.mychat.repository.UserRoleRepository
import moe.itsusinn.mychat.repository.entity.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userRoleRepository: UserRoleRepository

    /**
     * 校检密码是否正确
     */
    fun checkPassword(username: String, password: String): UserEntity? {
        val user = userRepository.findUserByUsername(username)
        return if (user == null || user.password != password) null
        else user
    }

    /**
     * 添加一个新用户
     * 有重复username则返回null
     */
    fun registerUser(username: String, password: String): UserEntity? {
        val repeat = userRepository.findUserByUsername(username)
        //如果有重复的则返回null
        if (repeat != null) return null
        val newUser = UserEntity {
            this.username = username
            this.password = password
        }
        userRepository.save(newUser)
        userRoleRepository.saveAsDefault(newUser.userID)
        return newUser
    }

}
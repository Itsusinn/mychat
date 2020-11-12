package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.repository.entity.User
import moe.itsusinn.mychat.repository.entity.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    @Autowired
    lateinit var database: Database

    fun add(newUser: User) {
        database.users.add(newUser)
    }

    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByAccount(account: String): User? = database.users.find { it.username eq account }

    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByUid(uid: Long): User? = database.users.find { it.uid eq uid }


}
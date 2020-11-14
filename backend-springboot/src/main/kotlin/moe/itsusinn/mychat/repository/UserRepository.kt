package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.repository.entity.UserEntity
import moe.itsusinn.mychat.repository.entity.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    val database: Database
) {
    fun save(newUserEntity: UserEntity) {
        database.users.add(newUserEntity)
    }
    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByUsername(account: String): UserEntity? = database.users.find { it.username eq account }

    /**
     * 查询用户,存在则返回实例
     * 不存在则返回空
     */
    fun findUserByUid(uid: Long): UserEntity? = database.users.find { it.userID eq uid }

}
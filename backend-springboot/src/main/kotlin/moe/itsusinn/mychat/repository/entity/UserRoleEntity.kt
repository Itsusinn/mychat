package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long

interface UserRoleEntity : Entity<UserRoleEntity> {
    companion object : Entity.Factory<UserRoleEntity>()

    var userID: Long
    var roleID: Long
}

object UserRoleTable : Table<UserRoleEntity>("user_role") {
    val userID = long("user_id").primaryKey().bindTo { it.userID }
    val roleID = long("role_id").primaryKey().bindTo { it.roleID }
}

val Database.userRoles get() = this.sequenceOf(UserRoleTable)
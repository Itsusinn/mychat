package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long

interface UserRole : Entity<UserRole> {
    companion object : Entity.Factory<UserRole>()

    var uid: Long
    var roleID: Long
}

object UserRoles : Table<UserRole>("user_role") {
    val uid = long("uid").primaryKey().bindTo { it.uid }
    val roleID = long("role_id").primaryKey().bindTo { it.roleID }
}

val Database.userRoles get() = this.sequenceOf(UserRoles)
package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long

interface RolePermission : Entity<RolePermission> {
    companion object : Entity.Factory<UserRole>()

    var roleID: Long
    var permissionID: Long
}

object RolePermissions : Table<RolePermission>("role_permission") {
    val roleID = long("role_id").primaryKey().bindTo { it.roleID }
    val permissionID = long("permission_id").primaryKey().bindTo { it.permissionID }
}

val Database.rolePermission get() = this.sequenceOf(RolePermissions)
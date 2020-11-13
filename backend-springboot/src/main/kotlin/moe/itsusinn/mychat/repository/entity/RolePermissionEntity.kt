package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long

interface RolePermissionEntity : Entity<RolePermissionEntity> {
    companion object : Entity.Factory<UserRoleEntity>()

    var roleID: Long
    var permissionID: Long
}


object RolePermissionTable : Table<RolePermissionEntity>("role_permission") {
    val roleID = long("role_id").primaryKey().bindTo { it.roleID }
    val permissionID = long("permission_id").primaryKey().bindTo { it.permissionID }
}

val Database.rolePermissions get() = this.sequenceOf(RolePermissionTable)
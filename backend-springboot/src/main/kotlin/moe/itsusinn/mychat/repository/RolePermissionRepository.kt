package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.repository.entity.RolePermissionTable
import moe.itsusinn.mychat.repository.entity.permissions
import moe.itsusinn.mychat.repository.entity.roles
import moe.itsusinn.mychat.security.permission.Permission
import moe.itsusinn.mychat.security.permission.Role
import moe.itsusinn.mychat.security.permission.RolePermission
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.entity.find
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository



@Repository
class RolePermissionRepository {

    @Autowired
    lateinit var database: Database

    fun readRolePermissions(): List<RolePermission> {
        val rolePermissions = mutableListOf<RolePermission>()
        return database
            .from(RolePermissionTable)
            .select()
            .map { row ->
                val roleID = row[RolePermissionTable.roleID]
                    ?: return@map null
                val permissionID = row[RolePermissionTable.permissionID]
                    ?: return@map null
                createRolePermission(roleID, permissionID)
            }
    }

    fun createRolePermission(roleID: Long, permissionID: Long): RolePermission? {
        val roleEntity = database.roles.find { it.roleID eq roleID } ?: return null
        val role = Role(roleID, roleEntity.name)
        val permissionEntity = database.permissions.find { it.permissionID eq permissionID } ?: return null
        val permission = Permission(permissionEntity.permissionID, permissionEntity.url)
        return RolePermission(role, permission)
    }
}
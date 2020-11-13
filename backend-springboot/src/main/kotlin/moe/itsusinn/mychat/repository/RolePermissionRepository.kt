package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.security.permission.RolePermission
import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class RolePermissionRepository {

    @Autowired
    lateinit var database: Database

    fun getRolePermissions(): List<RolePermission> {
        val rolePermissions = mutableListOf<RolePermission>()
        return rolePermissions
    }
}
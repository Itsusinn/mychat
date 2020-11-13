package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.repository.entity.RoleTable
import moe.itsusinn.mychat.repository.entity.UserRoleTable
import moe.itsusinn.mychat.security.permission.Role
import moe.itsusinn.mychat.security.permission.SecurityRole
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.springframework.stereotype.Repository


@Repository
class UserRoleRepository(
    val database: Database
) {
    fun getRolesByUid(uid: Long): List<Role> {
        val roleIDs = mutableListOf<Long>()
        val roles = mutableListOf<SecurityRole>()
        database.from(UserRoleTable)
            .select()
            .where { UserRoleTable.uid eq uid }
            .forEach {
                val roleID = it[UserRoleTable.roleID] ?: -1L
                roleIDs.add(roleID)
            }
        database.from(RoleTable)
            .select()
            .forEach { row ->
                val roleID = row[RoleTable.roleID] ?: -1L
                val name = row[RoleTable.name] ?: ""
                if (roleIDs.contains(roleID)) {
                    roles.add(Role(roleID, name))
                }
            }
        return roles
    }
}
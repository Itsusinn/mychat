package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.repository.entity.RoleTable
import moe.itsusinn.mychat.repository.entity.UserRoleEntity
import moe.itsusinn.mychat.repository.entity.UserRoleTable
import moe.itsusinn.mychat.repository.entity.userRoles
import moe.itsusinn.mychat.security.permission.Role
import moe.itsusinn.mychat.security.permission.SecurityRole
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.springframework.stereotype.Repository


@Repository
class UserRoleRepository(
    val database: Database
) {
    fun getRolesByUserID(userID: Long): List<Role> {
        val roleIDs = mutableListOf<Long>()
        val roles = mutableListOf<SecurityRole>()
        database.from(UserRoleTable)
            .select()
            .where { UserRoleTable.userID eq userID }
            .forEach {
                val roleID = it[UserRoleTable.roleID] ?: return@forEach
                roleIDs.add(roleID)
            }
        database.from(RoleTable)
            .select()
            .forEach { row ->
                val roleID = row[RoleTable.roleID] ?: return@forEach
                val name = row[RoleTable.name] ?: return@forEach
                if (roleIDs.contains(roleID)) {
                    roles.add(Role(roleID, name))
                }
            }
        return roles
    }

    fun saveAsDefault(userID: Long) {
        val newUserRole =
            UserRoleEntity {
                this.userID = userID
                this.roleID = 1
            }
        database.userRoles.add(newUserRole)
    }
}
package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.repository.entity.Roles
import moe.itsusinn.mychat.repository.entity.UserRoles
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
        database.from(UserRoles)
            .select()
            .where { UserRoles.uid eq uid }
            .forEach {
                val roleID = it[UserRoles.roleID] ?: -1L
                roleIDs.add(roleID)
            }
        database.from(Roles)
            .select()
            .forEach { row ->
                val roleID = row[Roles.roleID] ?: -1L
                val name = row[Roles.name] ?: ""
                if (roleIDs.contains(roleID)) {
                    roles.add(Role(roleID, name))
                }
            }
        return roles
    }
}
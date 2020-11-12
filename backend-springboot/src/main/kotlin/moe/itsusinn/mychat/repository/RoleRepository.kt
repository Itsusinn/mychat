package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.repository.entity.Role
import moe.itsusinn.mychat.repository.entity.Roles
import moe.itsusinn.mychat.repository.entity.UserRoles
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.springframework.stereotype.Repository

@Repository
class RoleRepository(
    val database: Database
) {
    fun getRolesByUid(uid: Long): List<Role> {
        val roleIDs = mutableListOf<Long>()
        val roles = mutableListOf<Role>()
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
                if (roleIDs.contains(roleID)) {
                    roles.add(
                        Roles.createEntity(row)
                    )
                }
            }
        return roles
    }
}
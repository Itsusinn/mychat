package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.UserRoleRepository
import moe.itsusinn.mychat.security.permission.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserRoleService {
    @Autowired
    lateinit var userRoleRepository: UserRoleRepository

    fun findRolesByUid(uid: Long): List<Role> {
        return userRoleRepository.getRolesByUserID(uid)
    }
}
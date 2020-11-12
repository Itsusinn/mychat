package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.RoleRepository
import moe.itsusinn.mychat.repository.UserRepository
import org.ktorm.database.Database
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class MyUserDetailsService(
    val database: Database,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findUserByAccount(username)
            ?: return null
        val roles = roleRepository.getRolesByUid(user.uid)
        return User(user.username, user.password, roles)
    }

}

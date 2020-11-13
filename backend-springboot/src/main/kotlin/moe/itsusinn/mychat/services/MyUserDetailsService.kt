package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.UserRepository
import moe.itsusinn.mychat.repository.UserRoleRepository
import moe.itsusinn.mychat.security.permission.SecurityUser
import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class MyUserDetailsService : UserDetailsService {
    @Autowired
    lateinit var database: Database

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userRoleRepository: UserRoleRepository

    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findUserByAccount(username)
            ?: throw UsernameNotFoundException("No such username:$username")
        val roles = userRoleRepository.getRolesByUid(user.uid)

        // 角色集合
        val authorities: MutableList<GrantedAuthority> = ArrayList()

        roles.forEach { role ->
            // 角色必须以`ROLE_`开头
            authorities.add(SimpleGrantedAuthority("ROLE_$role"))
        }
        return SecurityUser(user.uid, user.username, user.password, authorities)
    }

}

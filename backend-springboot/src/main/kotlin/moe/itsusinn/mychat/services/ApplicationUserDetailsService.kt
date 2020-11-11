package moe.itsusinn.mychat.services

import org.ktorm.database.Database
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class ApplicationUserDetailsService(
    val database: Database,
    val userService: UserService
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findUserByAccount(username)
            ?: throw UsernameNotFoundException(username)
        return User(user.username, user.password, emptyList())
    }

}

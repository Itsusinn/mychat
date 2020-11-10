package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.models.ApplicationUser
import moe.itsusinn.mychat.repository.ApplicationUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class ApplicationUserDetailsService(
    val applicationUserRepository: ApplicationUserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val applicationUser: ApplicationUser = applicationUserRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(username)
        return User(applicationUser.username, applicationUser.password, emptyList())
    }

}

package moe.itsusinn.mychat.security.permission

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

typealias SecurityUser = MyUserDetails

typealias SpringSecurityUser = org.springframework.security.core.userdetails.User

data class MyUserDetails(
    val uid: Long,
    private val username: String,
    private val password: String,
    private val authorities: MutableCollection<out GrantedAuthority>
) : UserDetails {

    override fun getAuthorities() = authorities
    override fun getPassword() = password
    override fun getUsername() = username

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return `true` if the user's account is valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isAccountNonExpired(): Boolean = true

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return `true` if the user is not locked, `false` otherwise
     */
    override fun isAccountNonLocked(): Boolean = true

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return `true` if the user's credentials are valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isCredentialsNonExpired(): Boolean = true

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return `true` if the user is enabled, `false` otherwise
     */
    override fun isEnabled(): Boolean = true
}
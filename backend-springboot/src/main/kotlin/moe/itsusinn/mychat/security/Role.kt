package moe.itsusinn.mychat.security

import org.springframework.security.core.GrantedAuthority

class Role(
    val roleID: Long,
    private val name: String
) : GrantedAuthority {

    override fun getAuthority(): String = name
}
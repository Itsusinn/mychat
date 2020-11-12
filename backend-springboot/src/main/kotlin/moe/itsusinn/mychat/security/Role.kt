package moe.itsusinn.mychat.security

import org.springframework.security.core.GrantedAuthority

typealias SecurityRole = Role

class Role(
    val roleID: Long,
    private val name: String
) : GrantedAuthority {

    override fun getAuthority(): String = name
}
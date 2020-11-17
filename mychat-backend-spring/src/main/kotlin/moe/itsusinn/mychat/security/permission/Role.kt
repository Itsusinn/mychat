package moe.itsusinn.mychat.security.permission

import org.springframework.security.core.GrantedAuthority

typealias SecurityRole = Role

class Role(
    val roleID: Long,
    val name: String
) : GrantedAuthority {

    override fun getAuthority(): String = name
}
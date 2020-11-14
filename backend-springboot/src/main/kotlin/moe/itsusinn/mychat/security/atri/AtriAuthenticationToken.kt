package moe.itsusinn.mychat.security.atri

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class AtriAuthenticationToken(
    private val uid: Long,
    private val uuid: String,
    private val authorities: MutableCollection<out GrantedAuthority>,
) : Authentication {
    private var isAuthenticated = false

    fun getUid(): Long = uid

    fun getUUID(): String = uuid

    override fun getName(): String = ""
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun isAuthenticated(): Boolean = true
    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.isAuthenticated = isAuthenticated
    }

    @Deprecated("Useless", ReplaceWith("getUid()"))
    override fun getPrincipal(): Any = uid

    @Deprecated("Useless", ReplaceWith(""))
    override fun getCredentials(): Any = Unit

    @Deprecated("Useless", ReplaceWith(""))
    override fun getDetails(): Any = Unit
}
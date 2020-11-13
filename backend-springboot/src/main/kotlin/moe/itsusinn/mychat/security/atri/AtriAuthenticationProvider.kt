package moe.itsusinn.mychat.security.atri

import moe.itsusinn.mychat.security.tool.parseToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.full.isSuperclassOf


/**
 * @see AbstractUserDetailsAuthenticationProvider
 * @see DaoAuthenticationProvider
 */
abstract class AtriAuthenticationProvider(
    private val atriValidator: AtriValidator
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication?): Authentication? {

        if (authentication == null || authentication !is AtriAuthenticationToken)
            return null
        //检查token是否过期
        val status = atriValidator.verifyToken("")
        if (!status) return null
        //若登录态仍然维持则认证该authentication
        authentication.isAuthenticated = true
        return authentication
    }

    override fun supports(authentication: Class<*>?): Boolean {
        if (authentication == null) return false
        return AtriAuthenticationToken::class.isSuperclassOf(authentication.kotlin)
    }
}

interface AtriValidator {
    /**
     * 要检查此Token是否在缓存中过期
     * @return 登录态仍然维持则返回true，否则false
     */
    fun verifyToken(rawToken: String): Boolean
}

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

/**
 * 登陆用过滤器
 */
class AtriAuthenticationFilter(url: String) :
    AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url, "POST")) {
    /**
     * The implementation should do one of the following:
     *  1. Return a populated authentication token for the authenticated user, indicating
     * successful authentication
     *  1. Return null, indicating that the authentication process is still in progress.
     * Before returning, the implementation should perform any additional work required to
     * complete the process.
     *  1. Throw an <tt>AuthenticationException</tt> if the authentication process fails
     * @return the authenticated user token, or null if authentication is incomplete.
     *
     * @throws AuthenticationException if authentication fails.
     */
    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): Authentication? {

        request ?: return null
        //从请求头中读取token

        return null
    }
}

/**
 *
 */
class AtriAuthorizationFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val rawToken = request.getHeader("Authentication") ?: return
        val tokenData = parseToken(rawToken)
        val roles = tokenData.roles
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        roles.forEach { role -> authorities.add(SimpleGrantedAuthority("ROLE_$role")) }
        val authentication = AtriAuthenticationToken(tokenData.uid, tokenData.uuid, authorities)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

}
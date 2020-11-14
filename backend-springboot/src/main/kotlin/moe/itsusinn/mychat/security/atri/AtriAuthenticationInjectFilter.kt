package moe.itsusinn.mychat.security.atri

import moe.itsusinn.mychat.security.tool.parseToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Authentication injector by intercept request
 */
class AtriAuthenticationInjectFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {
    /**
     * core filter
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        //read token from `Authentication` header
        val rawToken = request.getHeader("Authorization") ?: defaultHeader
        val tokenData = parseToken(rawToken)
        val roles = tokenData.roles
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        //parse authorities instance for raw string
        roles.forEach { role -> authorities.add(SimpleGrantedAuthority(role)) }
        //create Authorization instance
        val authentication = AtriAuthenticationToken(tokenData.uid, tokenData.uuid, authorities)
        //inject Authorization instance
        SecurityContextHolder.getContext().authentication = authentication
        //continue to next filter
        chain.doFilter(request, response)
    }
}

const val defaultHeader =
    "eyJ1aWQiOjAsInV1aWQiOiJVVUlEIiwiY3JlYXRlZCI6MTYwNTM3MjUxMjQxNywicm9sZXMiOlsiR1VFU1QiXX0="
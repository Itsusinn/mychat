package moe.itsusinn.mychat.security.atri

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
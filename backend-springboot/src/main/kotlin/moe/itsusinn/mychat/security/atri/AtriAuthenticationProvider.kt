package moe.itsusinn.mychat.security.atri

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import kotlin.reflect.full.isSuperclassOf

/**
 * @see AbstractUserDetailsAuthenticationProvider
 * @see DaoAuthenticationProvider
 */
class AtriAuthenticationProvider(
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
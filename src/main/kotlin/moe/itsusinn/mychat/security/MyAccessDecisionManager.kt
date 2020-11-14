package moe.itsusinn.mychat.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


/**
 * 决策器
 */
@Component
class MyAccessDecisionManager : AccessDecisionManager {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(MyAccessDecisionManager::class.java)
    }
    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     *
     * @param authentication 包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities。
     * @param object  就是FilterInvocation对象，可以得到request等web资源
     * @param configAttributes configAttributes是本次访问需要的权限
     */
    @Throws(SpringSecurityAccessDeniedException::class, InsufficientAuthenticationException::class)
    override fun decide(
        authentication: Authentication,
        `object`: Any?,
        configAttributes: Collection<ConfigAttribute>?
    ) {

        //该路由不需要权限，即attributes为空 directly return
        if (configAttributes.isNullOrEmpty()) return

        val grantedAuthorities = authentication.authorities

        if (grantedAuthorities.isNullOrEmpty())
            throw InsufficientAuthenticationException("Credentials are not trusted")
        for (configAttribute in configAttributes) {
            for (grantedAuthority in grantedAuthorities) {
                val need = configAttribute.attribute.trim()
                val has = grantedAuthority.authority.trim()
                //只要有一个匹配就return
                if (need == has) return
            }
        }
        throw SpringSecurityAccessDeniedException("Access Deny")

    }

    /**
     * 表示此AccessDecisionManager是否能够处理传递的ConfigAttribute呈现的授权请求
     */
    override fun supports(configAttribute: ConfigAttribute): Boolean = true

    /**
     * 表示当前AccessDecisionManager实现是否能够为指定的安全对象（方法调用或Web请求）提供访问控制决策
     */
    override fun supports(aClass: Class<*>?): Boolean = true


}

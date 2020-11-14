package moe.itsusinn.mychat.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.SecurityMetadataSource
import org.springframework.security.access.intercept.AbstractSecurityInterceptor
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*

/**
 * 自定义FilterSecurityInterceptor
 * 以使用自定义的 AccessDecisionManager 和 securityMetadataSource。
 */
@Component
class MyFilterSecurityInterceptor : AbstractSecurityInterceptor(), Filter {

    @Autowired
    private lateinit var securityMetadataSource: FilterInvocationSecurityMetadataSource

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        servletRequest: ServletRequest?,
        servletResponse: ServletResponse?,
        filterChain: FilterChain?
    ) {
        val filterInvocation = FilterInvocation(servletRequest, servletResponse, filterChain)
        invoke(filterInvocation)
    }

    @Throws(IOException::class, ServletException::class)
    operator fun invoke(filterInvocation: FilterInvocation) {
        val token = super.beforeInvocation(filterInvocation)
        try {
            //执行下一个拦截器
            filterInvocation.chain.doFilter(filterInvocation.request, filterInvocation.response)
        } finally {
            super.afterInvocation(token, null)
        }
    }

    override fun getSecureObjectClass(): Class<*> = FilterInvocation::class.java

    override fun obtainSecurityMetadataSource(): SecurityMetadataSource = securityMetadataSource
}

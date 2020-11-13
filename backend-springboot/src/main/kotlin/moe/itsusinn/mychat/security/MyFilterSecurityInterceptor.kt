package moe.itsusinn.mychat.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.SecurityMetadataSource
import org.springframework.security.access.intercept.AbstractSecurityInterceptor
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*

@Component
class MyFilterSecurityInterceptor : AbstractSecurityInterceptor(), Filter {

    @Autowired
    private val securityMetadataSource: FilterInvocationSecurityMetadataSource? = null

    @Autowired
    fun setMyAccessDecisionManager(
        myAccessDecisionManager: MyAccessDecisionManager?
    ) {
        super.setAccessDecisionManager(myAccessDecisionManager)
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        servletRequest: ServletRequest?,
        servletResponse: ServletResponse?,
        filterChain: FilterChain?
    ) {
        val fi = FilterInvocation(servletRequest, servletResponse, filterChain)
        invoke(fi)
    }

    @Throws(IOException::class, ServletException::class)
    operator fun invoke(fi: FilterInvocation) {
        val token = super.beforeInvocation(fi)
        try {
            //执行下一个拦截器
            fi.chain.doFilter(fi.request, fi.response)
        } finally {
            super.afterInvocation(token, null)
        }
    }

    override fun getSecureObjectClass(): Class<*> {
        return FilterInvocation::class.java
    }

    override fun obtainSecurityMetadataSource(): SecurityMetadataSource {
        return securityMetadataSource!!
    }
}

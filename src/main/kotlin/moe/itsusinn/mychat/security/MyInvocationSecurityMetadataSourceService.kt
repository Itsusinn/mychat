package moe.itsusinn.mychat.security

import moe.itsusinn.mychat.repository.RolePermissionRepository
import moe.itsusinn.mychat.security.permission.RolePermission
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import java.util.*


/**
 * 用来储存请求与权限的对应关系
 */
@Component
class MyInvocationSecurityMetadataSourceService : FilterInvocationSecurityMetadataSource {
    @Autowired
    lateinit var rolePermissionRepository: RolePermissionRepository

    companion object {
        /**
         * 每一个资源所需要的角色 决策器会用到
         */
        private val mapper = HashMap<String, MutableCollection<ConfigAttribute>>()
    }

    /**
     * @return which roles this resource need
     */
    @Throws(IllegalArgumentException::class)
    override fun getAttributes(o: Any): Collection<ConfigAttribute>? {
        //object 中包含用户请求的request 信息
        val request = (o as FilterInvocation).httpRequest
        mapper.keys.forEach { url ->
            if (AntPathRequestMatcher(url).matches(request)) return mapper[url]
        }
        return null
    }

    /**
     * init the mapper
     */
    fun loadResourceDefine() {
        //权限资源 和 角色对应的表  也就是 角色权限 中间表
        val rolePermissions: List<RolePermission> = rolePermissionRepository.readRolePermissions()

        //某个资源 可以被哪些角色访问
        for (rolePermission in rolePermissions) {
            val url: String = rolePermission.permission.url
            val roleName: String = rolePermission.role.name
            val role: ConfigAttribute = SecurityConfig(roleName)

            if (mapper[url] == null) mapper[url] = ArrayList()
            //将role添加到permission对应的url上
            mapper[url]!!.add(role)
        }
    }

    override fun getAllConfigAttributes(): Collection<ConfigAttribute>? {
        loadResourceDefine()
        return null
    }

    override fun supports(aClass: Class<*>?): Boolean = true
}

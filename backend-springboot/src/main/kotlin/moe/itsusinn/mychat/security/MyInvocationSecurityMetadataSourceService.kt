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
         * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
         */
        private lateinit var map: HashMap<String, MutableCollection<ConfigAttribute>>
    }

    /**
     * 返回请求的资源需要的角色
     */
    @Throws(IllegalArgumentException::class)
    override fun getAttributes(o: Any): Collection<ConfigAttribute>? {
        if (map.isNullOrEmpty()) {
            loadResourceDefine()
        }
        //object 中包含用户请求的request 信息
        val request = (o as FilterInvocation).httpRequest
        val it: Iterator<String> = map.keys.iterator()
        while (it.hasNext()) {
            val url = it.next()
            if (AntPathRequestMatcher(url).matches(request)) {
                return map[url]!!
            }
        }
        return null
    }

    /**
     * 初始化 所有资源 对应的角色s
     */
    fun loadResourceDefine() {
        //权限资源 和 角色对应的表  也就是 角色权限 中间表
        val rolePermissions: List<RolePermission> = rolePermissionRepository.getRolePermissions()

        //某个资源 可以被哪些角色访问
        for (rolePermission in rolePermissions) {
            val url: String = rolePermission.permission.url
            val roleName: String = rolePermission.role.name
            val role: ConfigAttribute = SecurityConfig(roleName)
            map[url]?.add(role) ?: run {
                val list: MutableList<ConfigAttribute> = ArrayList()
                list.add(role)
                map[url] = list
            }
        }
    }

    override fun getAllConfigAttributes(): Collection<ConfigAttribute>? = null
    override fun supports(aClass: Class<*>?): Boolean = true
}

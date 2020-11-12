package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.repository.PermissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import java.util.*


@Component
class MyInvocationSecurityMetadataSourceService : FilterInvocationSecurityMetadataSource {
    @Autowired
    lateinit var permissionRepository: PermissionRepository

    /**
     * 返回请求的资源需要的角色
     */
    @Throws(IllegalArgumentException::class)
    override fun getAttributes(o: Any): Collection<ConfigAttribute>? {
        if (null == map) {
            loadResourceDefine()
        }
        //object 中包含用户请求的request 信息
        val request = (o as FilterInvocation).httpRequest
        val it: Iterator<String> = map!!.keys.iterator()
        while (it.hasNext()) {
            val url = it.next()
            if (AntPathRequestMatcher(url).matches(request)) {
                return map!![url]!!
            }
        }
        return null
    }

    override fun getAllConfigAttributes(): Collection<ConfigAttribute>? {
        return null
    }

    override fun supports(aClass: Class<*>?): Boolean {
        return true
    }

    /**
     * 初始化 所有资源 对应的角色
     */
    fun loadResourceDefine() {
        map = HashMap(16)
        //权限资源 和 角色对应的表  也就是 角色权限 中间表
        val rolePermissions: List<RolePermisson> = permissionMapper.getRolePermissions()

        //某个资源 可以被哪些角色访问
        for (rolePermission in rolePermissions) {
            val url: String = rolePermission.getUrl()
            val roleName: String = rolePermission.getRoleName()
            val role: ConfigAttribute = SecurityConfig(roleName)
            if (map!!.containsKey(url)) {
                map!![url]!!.add(role)
            } else {
                val list: MutableList<ConfigAttribute> = ArrayList()
                list.add(role)
                map!![url] = list
            }
        }
    }

    companion object {
        /**
         * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
        </ConfigAttribute> */
        private var map: HashMap<String, MutableCollection<ConfigAttribute>>? = null
    }
}

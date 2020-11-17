package moe.itsusinn.mychat.security.permission


typealias SecurityRolePermission = RolePermission

data class RolePermission(
    val role: Role,
    val permission: Permission
)

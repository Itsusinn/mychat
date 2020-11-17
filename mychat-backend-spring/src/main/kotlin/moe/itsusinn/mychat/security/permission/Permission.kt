package moe.itsusinn.mychat.security.permission

typealias SecurityPermission = Permission

data class Permission(
    val permissionID: Long,
    val url: String
)
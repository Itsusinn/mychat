package moe.itsusinn.mychat.security.permission

import org.springframework.security.core.GrantedAuthority

typealias SecurityUser = User

typealias SpringSecurityUser = org.springframework.security.core.userdetails.User

class User(
    uid: Long,
    username: String,
    password: String,
    authorities: MutableCollection<out GrantedAuthority>
) : SpringSecurityUser(username, password, authorities)
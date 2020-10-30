package moe.itsusinn.mychat.route.jwt

import io.ktor.auth.*

data class UidPrincipal(val uid: Int,val uuid:String) : Principal

data class AccountPasswordCredential(val account:String,val password:String): Credential
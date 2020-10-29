package moe.itsusinn.mychat.route.jwt

import io.ktor.auth.*

data class AccountPasswordCredential(val account:String,val password:String):Credential
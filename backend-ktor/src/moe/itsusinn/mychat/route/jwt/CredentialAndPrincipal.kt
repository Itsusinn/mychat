package moe.itsusinn.mychat.route.jwt

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.util.pipeline.*
import moe.itsusinn.mychat.illegalStage

data class UidPrincipal(val uid: Int,val uuid:String) : Principal

data class AccountPasswordCredential(val account:String,val password:String): Credential

suspend fun PipelineContext<Unit, ApplicationCall>.principal():UidPrincipal{
    return call.principal()
        ?: illegalStage("No principal decoded")
}
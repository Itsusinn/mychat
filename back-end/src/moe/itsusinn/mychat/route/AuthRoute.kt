package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import moe.itsusinn.mychat.route.jwt.AccountPasswordCredential
import moe.itsusinn.mychat.route.jwt.JwtConfig
import moe.itsusinn.mychat.route.jwt.UidPrincipal
import moe.itsusinn.mychat.service.UserService

fun Application.auth(){

    install(Authentication) {
        //设置jwt
        jwt {
            verifier(JwtConfig.verifier)
            validate {
                UidPrincipal(it.payload.getClaim("uid").asInt())
            }
        }
        //设置跨域
        install(CORS){
            header(HttpHeaders.AccessControlAllowHeaders)
            header(HttpHeaders.ContentType)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            anyHost()
        }
    }

}

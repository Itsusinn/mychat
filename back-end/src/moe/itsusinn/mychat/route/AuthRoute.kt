package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.err
import moe.itsusinn.mychat.route.jwt.AccountPasswordCredential
import moe.itsusinn.mychat.route.jwt.JwtConfig
import moe.itsusinn.mychat.route.jwt.AccessTokenPrincipal
import moe.itsusinn.mychat.route.jwt.RefreshTokenPrincipal
import moe.itsusinn.mychat.service.UserService

fun Application.auth(){

    install(Authentication) {
        //设置jwt
        jwt("accessToken") {
            verifier(JwtConfig.verifier)
            validate {
                //TODO 业务逻辑
                AccessTokenPrincipal(it.payload.claims["uid"]!!.asInt())
            }
        }
        jwt("refreshToken") {
            verifier(JwtConfig.verifier)
            validate {
                //TODO 业务逻辑
                RefreshTokenPrincipal(it.payload.claims["uid"]!!.asInt())
            }
        }
        install(DefaultHeaders){

        }
        //设置跨域
        install(CORS){
            header(HttpHeaders.AccessControlAllowHeaders)
            header(HttpHeaders.ContentType)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            anyHost()
        }
        routing {
            post("login") {
                //从请求体中接收凭证
                val credential = call.receive<AccountPasswordCredential>()
                //从mysql中获取User实例
                val user = UserService.findUserByAccount(credential.account)
                if (user==null||user.password != credential.password){
                    //该用户未注册或密码错误
                    err("Invalid Credentials")
                }
                //根据uid签发RefreshToken
                val token = JwtConfig.makeRefreshToken(user.uid)
                //返回Token令牌
                call.respond(mapOf("token" to token))
            }
        }
    }

}

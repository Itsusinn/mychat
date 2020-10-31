package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.err
import moe.itsusinn.mychat.route.jwt.*
import moe.itsusinn.mychat.service.UserService

fun Application.auth(){

    routing {

        //登陆路由,从此路由返回accessToken
        post("login") {
            //从请求体中接收凭证
            val credential = call.receiveOrNull<AccountPasswordCredential>()
                    ?: err("No AccountPasswordCredential Decoded")
            //登录该用户
            val uidPrincipal = UserService.login(credential.account,credential.password)
                    ?: err("UidPrincipal Create Failed")
            //UUID作为jwt标识符
            val token = JwtConfig.makeToken(uidPrincipal.uid,uidPrincipal.uuid)
            //返回accessToken令牌
            call.respond(mapOf("token" to token))
        }

        authenticate {
            get("logout"){
                val principal = call.principal<UidPrincipal>()
                        ?: err("No principal decoded")
                UserService.logout(principal.uid,principal.uuid)
                call.respond("Logout Successfully")
            }
        }
    }

}

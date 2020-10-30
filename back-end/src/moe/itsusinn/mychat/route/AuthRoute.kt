package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.err
import moe.itsusinn.mychat.route.jwt.*
import moe.itsusinn.mychat.service.UserService
import moe.itsusinn.mychat.service.tokenList
import java.util.*

fun Application.auth(){

    routing {

        //登陆路由,从此路由返回accessToken
        post("login") {
            //从请求体中接收凭证
            val credential = call.receiveOrNull<AccountPasswordCredential>()
                    ?: err("No AccountPasswordCredential Decoded")
            //从mysql中获取User实例,并校检密码的正确性
            val user = UserService.findUserByAccount(credential.account)
            //用户不存在,或密码错误
            if (user==null||user.password != credential.password){
                //该用户未注册或密码错误
                err("Invalid Credentials")
            }
            //UUID作为jwt标识符
            val uuid = UUID.randomUUID()
            val token = JwtConfig.makeToken(user.uid,uuid)

            tokenList.add("TOKEN:${user.uid}:$uuid")

            //返回accessToken令牌
            call.respond(mapOf("token" to token))
        }

        authenticate {
            get("logout"){
                val principal = call.principal<UidPrincipal>() ?: err("No principal decoded")
                tokenList.remove("TOKEN:${principal.uid}:${principal.uuid}")
                call.respond("Logout Successfully")
            }
        }
    }

}

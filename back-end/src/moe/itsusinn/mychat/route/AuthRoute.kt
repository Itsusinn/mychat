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
import moe.itsusinn.mychat.route.jwt.*
import moe.itsusinn.mychat.service.UserService

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
            val token = JwtConfig.makeToken(user.uid)
            //TODO 保存到Redis
            //返回accessToken令牌
            call.respond(mapOf("token" to token))
        }
    }

}

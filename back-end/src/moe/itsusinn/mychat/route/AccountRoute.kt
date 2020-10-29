package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.route.jwt.AccountPasswordCredential
import moe.itsusinn.mychat.route.jwt.JwtConfig
import moe.itsusinn.mychat.route.jwt.UidPrincipal
import moe.itsusinn.mychat.service.UserService

fun Application.account(){
    /**
     * 使用JWT的登陆路由
     */
    routing {
        post("login") {
            //从请求体中接收凭证
            val credential = call.receive<AccountPasswordCredential>()
            //从mysql中获取User实例
            val user = UserService.findUserByAccount(credential.account)
            if (user==null||user.password != credential.password){
                //该用户未注册或密码错误
                error("Invalid Credentials")
            }
            //根据uid签发Token
            val token = JwtConfig.makeToken(UidPrincipal(user.uid))
            //返回Token令牌
            call.respond(mapOf("token" to token))
        }
    }
}
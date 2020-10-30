package moe.itsusinn.mychat

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.server.netty.*
import moe.itsusinn.mychat.route.account
import moe.itsusinn.mychat.route.auth
import moe.itsusinn.mychat.route.basic
import moe.itsusinn.mychat.route.jwt.UidPrincipal
import moe.itsusinn.mychat.route.jwt.JwtConfig
import moe.itsusinn.mychat.service.tokenList

fun main(args: Array<String>) = EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Authentication) {
        //设置jwt
        jwt {
            verifier(JwtConfig.verifier)
            validate {

                val uid = it.payload.getClaim("uid").asString().toInt()
                val uuid = it.payload.id

                if (tokenList.contains("TOKEN:$uid:$uuid")){
                    //延长失效时间
                    tokenList.add("TOKEN:$uid:$uuid")
                    UidPrincipal(uid,uuid)
                }else{
                    null
                }
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
    //日志打印
    install(CallLogging)
    //内容协商,使用Jackson
    install(ContentNegotiation) {
        jackson {
            //美化输出,非必要
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    //无顺序
    basic()
    auth()
    account()
}

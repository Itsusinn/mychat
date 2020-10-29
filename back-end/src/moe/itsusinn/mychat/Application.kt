package moe.itsusinn.mychat

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.server.netty.*
import io.ktor.util.*
import moe.itsusinn.mychat.route.account
import moe.itsusinn.mychat.route.auth
import moe.itsusinn.mychat.route.basic

fun main(args: Array<String>) = EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    //日志打印
    install(CallLogging)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    //无顺序
    basic()
    auth()
    account()
    //测试用路由
    routing {
        route("test"){
            get {
                call.respond("Hello")
            }
        }
    }
}

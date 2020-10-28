package org.meowcat

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.sessions.*
import org.ktorm.entity.add
import org.meowcat.data.MySession
import org.meowcat.data.UserIdTokenPrincipal
import org.meowcat.event.CommentAddEvent
import org.meowcat.event.LoginEvent
import org.meowcat.sqldata.database
import org.meowcat.sqldata.Comment

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Sessions){
        cookie<MySession>("MyCookie")
    }

    install(Authentication) {
        session<MySession>(name = "auth"){
            validate {credentials ->
                if (org.meowcat.data.check(credentials.account,credentials.token)){
                    UserIdTokenPrincipal(credentials.account,credentials.token)
                }else{
                    null
                }
            }
        }


        install(CORS){
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            header(HttpHeaders.AccessControlAllowHeaders)
            header(HttpHeaders.ContentType)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            anyHost()
        }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        route("test"){
            get {
                call.respond("Hello")
            }
        }
        val version ="v1"

        route("/api/$version/"){
            route("login"){
                post {
                    val loginEvent = call.receiveOrNull<LoginEvent>()
                    if (loginEvent != null){
                        val token = "TEST_TOKEN" //TODO 验证密码是否正确,并用JWT生成token
                        call.response.headers.append("Set-Cookie","TOKEN=$token")
                    }else call.respond(HttpStatusCode.BadRequest,"参数错误").also { return@post }
                    println(loginEvent)
                }
            }
            authenticate("auth") {
                route("comment"){
                    post("add") {
                        val commentAddEvent = call.receiveOrNull<CommentAddEvent>()
                        if (commentAddEvent != null) {
                            val newCommend = Comment{
                                nick = commentAddEvent.nick
                                email = commentAddEvent.email
                                content = commentAddEvent.content
                                subject = commentAddEvent.subject
                            }
                            database.comments.add(newCommend)
                            call.respond(HttpStatusCode.Accepted,"${newCommend.id}")
                        }else call.respond(HttpStatusCode.BadRequest,"参数错误").also { return@post }
                        println(commentAddEvent)
                    }
                    get ("get"){
                        val id = call.parameters["subject"]
                        if (id.isNullOrEmpty()) call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}

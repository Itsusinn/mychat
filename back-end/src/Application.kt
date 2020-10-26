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
import org.ktorm.entity.add
import org.meowcat.event.CommentAdd
import org.meowcat.sqldata.database
import org.meowcat.sqldata.Comment

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Authentication) {
        install(CORS){
            method(HttpMethod.Options)
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Put)
            method(HttpMethod.Delete)
            method(HttpMethod.Patch)
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
        val version ="v1"

        route("/api/$version/"){
            route("comment"){
                post("add") {
                    val request = call.receiveOrNull<CommentAdd>()
                    if (request != null) {
                        val newCommend = Comment{
                            nick = request.nick
                            email = request.email
                            content = request.content
                            subject = request.subject
                        }
                        database.comments.add(newCommend)
                        call.respond(HttpStatusCode.Accepted,"${newCommend.id}")
                    }else call.respond(HttpStatusCode.BadRequest,"参数错误").also { return@post }
                    println(request)
                }
                get ("get"){
                    val id = call.parameters["subject"]
                    if (id.isNullOrEmpty()) call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}

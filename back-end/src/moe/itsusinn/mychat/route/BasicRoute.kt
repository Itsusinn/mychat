package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.ktorm.entity.add
import moe.itsusinn.mychat.event.CommentAddEvent
import moe.itsusinn.mychat.route.jwt.UidPrincipal
import moe.itsusinn.mychat.sql.comments
import moe.itsusinn.mychat.sql.data.Comment
import moe.itsusinn.mychat.sql.database

fun Application.basic(){

    routing {
        route("/api/$version/"){

            authenticate {
                route("comment"){
                    post("add") {
                        //解码&签名认证
                        val principal = call.principal<UidPrincipal>() ?: error("No principal decoded")
                        val uid = principal.uid
                        val commentAddEvent = call.receiveOrNull<CommentAddEvent>()
                        if (commentAddEvent != null) {
                            val newComment = Comment{
                                nick = commentAddEvent.nick
                                email = commentAddEvent.email
                                content = commentAddEvent.content
                                subject = commentAddEvent.subject
                            }
                            database.comments.add(newComment)
                            call.respond(HttpStatusCode.Accepted,"${newComment.id}")
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
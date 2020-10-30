package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.CommentAddEvent
import moe.itsusinn.mychat.err
import org.ktorm.entity.add
import moe.itsusinn.mychat.route.jwt.UidPrincipal
import moe.itsusinn.mychat.sql.comments
import moe.itsusinn.mychat.sql.data.Comment
import moe.itsusinn.mychat.sql.database
import moe.itsusinn.mychat.version

fun Application.basic(){

    routing {
        route("/api/$version/"){
            authenticate {
                route("comment"){
                    post("add") {
                        //解码&签名认证
                        val principal = call.principal<UidPrincipal>()
                                ?: err("No principal decoded")
                        var uid = principal.uid

                        val commentAddEvent = call.receiveOrNull<CommentAddEvent>()
                                ?: err("参数错误")

                        val newComment = Comment{
                            email = commentAddEvent.email
                            uid = commentAddEvent.uid
                            content = commentAddEvent.content
                            subject = commentAddEvent.subject
                        }
                        database.comments.add(newComment)
                        call.respond(HttpStatusCode.Accepted,"${newComment.id}")

                        println(commentAddEvent)
                    }
                }
            }
        }
    }
}
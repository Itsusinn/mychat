package org.meowcat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.ktorm.entity.add
import org.meowcat.data.CommentAddEvent
import org.meowcat.extend.comments
import org.meowcat.sqldata.Comment
import org.meowcat.sqldata.database

@KtorExperimentalAPI
fun Application.route(){
    val version ="v1"

    routing {
        route("/api/$version/"){

            authenticate("org.meowcat.route.auth") {
                route("comment"){
                    post("add") {
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
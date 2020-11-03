package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.CommentAddEvent
import moe.itsusinn.mychat.err
import moe.itsusinn.mychat.route.jwt.UidPrincipal
import moe.itsusinn.mychat.route.jwt.principal
import moe.itsusinn.mychat.service.PostService

fun Route.commentRoute(){
    route("comment"){
        authenticate {
            post("add") {
                //解码&签名认证
                val principal = principal()
                val commentAddEvent = call.receiveOrNull<CommentAddEvent>()
                        ?: err("参数错误")

                val uid = principal.uid
                val content = commentAddEvent.content
                val subject = commentAddEvent.subject

                val newComment = PostService.addComment(uid,content,subject)
                //return the comment's id
                val result = createResult(Status.Success,"id" to newComment.id)
                call.respond(result)
            }
        }

    }
}
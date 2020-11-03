package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.PostAddEvent
import moe.itsusinn.mychat.err
import moe.itsusinn.mychat.route.jwt.UidPrincipal
import moe.itsusinn.mychat.route.jwt.principal
import moe.itsusinn.mychat.service.PostService

fun Route.postRoute(){
    authenticate {
        route("post"){
            post("add") {
                val principal = principal()
                val postAddEvent = call.receiveOrNull<PostAddEvent>()
                    ?: err("Wrong PostAddEvent Format")

                val title = postAddEvent.title
                    ?: err("No Title")
                val newPost = PostService.addPost(principal.uid,title)
                call.respond(newPost.id)
            }
            post ("del"){
                //TO TO
            }
        }
    }
}
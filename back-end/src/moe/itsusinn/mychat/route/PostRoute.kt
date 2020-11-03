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
            //add a new post
            post("add") {
                val principal = principal()
                val postAddEvent = call.receiveOrNull<PostAddEvent>()
                    ?: err("Wrong PostAddEvent Format")

                val title = postAddEvent.title
                    ?: err("No Title")
                val newPost = PostService.addPost(principal.uid,title)
                val result = createResult(Status.Success,"post-id" to newPost.id)
                call.respond(result)
            }
            post ("del"){
                //TO TO
            }
            //get all the posts in the database
            get("getall") {
                val all = PostService.getAllPosts()
                val result = createResult(Status.Success,all)
                call.respond(result)
            }
        }
    }
}
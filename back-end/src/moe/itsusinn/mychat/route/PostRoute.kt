package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.GetCommentsEvent
import moe.itsusinn.mychat.PostAddEvent
import moe.itsusinn.mychat.illegalArgument
import moe.itsusinn.mychat.illegalStage
import moe.itsusinn.mychat.route.jwt.principal
import moe.itsusinn.mychat.service.PostService

fun Route.postRoute(){
    authenticate {
        route("post"){
            //add a new post
            post("add") {
                val principal = principal()
                val postAddEvent = call.receiveOrNull<PostAddEvent>()
                    ?: illegalStage("Decode PostAddEvent Failed")

                val title = postAddEvent.title
                    ?: illegalArgument("No Title param")

                val newPost = PostService.addPost(principal.uid,title)
                val result = createResult(Status.Success,"post-id" to newPost.post_id)
                call.respond(result)
            }
            post ("del"){
                //TO TO
            }
            //get all the posts in the database
            get("getall") {
                val all = PostService.getAllPosts()
                val result = createResult(Status.Success,"data" to all)
                call.respond(result)
            }
            post("get-comments"){
                val getCommentsEvent = call.receiveOrNull<GetCommentsEvent>()
                    ?: illegalStage("Decode GetCommentEvent Failed")

                val postID = getCommentsEvent.post_id
                    ?: illegalArgument("No postID param")
                //get all the comments
                val comments = PostService.getComments(postID)
                //format result
                val result = createResult(Status.Success,"data" to comments)
                //http respond
                call.respond(result)
            }

        }
    }
}
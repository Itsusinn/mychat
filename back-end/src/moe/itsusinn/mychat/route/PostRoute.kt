package moe.itsusinn.mychat.route

import io.ktor.auth.*
import io.ktor.routing.*

fun Route.postRoute(){
    authenticate {
        route("post"){
            post("add") {
                //TODO
            }
            post ("del"){
                //TODO
            }
        }
    }
}
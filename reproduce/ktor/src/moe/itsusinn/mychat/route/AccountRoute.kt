package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.UserRegisterEvent
import moe.itsusinn.mychat.illegalStage
import moe.itsusinn.mychat.service.UserService

fun Route.accountRoute(){
    /**
     * 使用JWT的注冊路由
     */
    post("register") {
        val userRegisterEvent = call.receiveOrNull<UserRegisterEvent>()
                ?: illegalStage("UserRegisterEvent Decode Error")
        userRegisterEvent.apply {
            val user = UserService.addNewUser(account, nick, password)
                    ?: illegalStage("User create failed")
            call.respond(mapOf("uid" to user.uid))
        }
    }
    authenticate {
        post("password/change") {
            //TODO
        }
    }
}
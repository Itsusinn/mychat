package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import moe.itsusinn.mychat.UserRegisterEvent
import moe.itsusinn.mychat.err
import moe.itsusinn.mychat.service.UserService

fun Route.accountRoute(){
    /**
     * 使用JWT的登陆路由
     */
    post("register") {
        val userRegisterEvent = call.receiveOrNull<UserRegisterEvent>()
                ?: err("UserRegisterEvent Decode Error")
        userRegisterEvent.apply {
            val user = UserService.addNewUser(account, nick, password)
                    ?: err("User create failed")
            call.respond("uid: ${user.uid}")
        }
    }
    authenticate {
        post("password/change") {
            //TODO
        }
    }
}
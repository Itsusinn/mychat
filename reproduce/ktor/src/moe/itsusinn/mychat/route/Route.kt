package moe.itsusinn.mychat.route

import io.ktor.application.*
import io.ktor.routing.*
import moe.itsusinn.mychat.version

fun Application.routes(){
    routing {
        route("/api/$version/"){
            //认证路由
            authRoute()
            //评论路由
            commentRoute()
            //账户相关路由
            accountRoute()
            //帖子相关路由
            postRoute()
        }
    }
}

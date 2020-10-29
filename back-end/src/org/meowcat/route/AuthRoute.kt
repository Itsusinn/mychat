package org.meowcat.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.meowcat.route.jwt.JwtConfig
import org.meowcat.route.jwt.UserSource
import org.meowcat.route.jwt.UserSourceImpl

@KtorExperimentalAPI
fun Application.auth(){
    val userSource: UserSource = UserSourceImpl()
    install(Authentication) {
        /**
         * Setup the JWT authentication to be used in [Routing].
         * If the token is valid, the corresponding [User] is fetched from the database.
         * The [User] can then be accessed in each [ApplicationCall].
         */
        jwt {
            verifier(JwtConfig.verifier)
            realm = "mychat.io"
            validate {
                it.payload.getClaim("uid").asInt()?.let(userSource::findUserByUid)
            }
        }
    }
    val redis = RedisClient()
    /**
     * A public login [Route] used to obtain JWTs
     */
    routing {
        post("login") {
            val credentials = call.receive<UserPasswordCredential>()
            val user = userSource.findUserByCredentials(credentials)
            val token = JwtConfig.makeToken(user)
            call.respondText(token)
        }
    }


    install(Authentication) {

        install(CORS){
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            header(HttpHeaders.AccessControlAllowHeaders)
            header(HttpHeaders.ContentType)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            anyHost()
        }
    }
}
package org.meowcat

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.meowcat.route.auth
import org.meowcat.route.route
import org.meowcat.sqldata.database

fun main(args: Array<String>) = run {
    EngineMain.main(args)
}

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    database()

    auth()

    install(CallLogging)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    route()

    routing {
        route("test"){
            get {
                call.respond("Hello")
            }
        }
    }
}

package moe.itsusinn.mychat.extend

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.response.*
import io.ktor.util.*

@KtorExperimentalAPI
val appConfig = HoconApplicationConfig(ConfigFactory.load())

suspend fun err(call:ApplicationCall, message: Any): Nothing {
    call.respond(message.toString())
    throw IllegalStateException(message.toString())
}



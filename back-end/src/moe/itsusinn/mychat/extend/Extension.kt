package moe.itsusinn.mychat.extend

import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.util.*

@KtorExperimentalAPI
val appConfig = HoconApplicationConfig(ConfigFactory.load())



package org.meowcat.sqldata

import io.ktor.application.*
import io.ktor.util.*
import org.ktorm.database.Database

@KtorExperimentalAPI
fun Application.database(){
    val address = environment.config.property("mysql.address").getString()
    val port = environment.config.property("mysql.port").getString()
    val name = environment.config.property("mysql.database").getString()
    val user = environment.config.property("mysql.user").getString()
    val password = environment.config.property("mysql.password").getString()
    database = Database.connect("jdbc:mysql://$address:$port/$name?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
            driver = "com.mysql.cj.jdbc.Driver",
            user = user,
            password = password)
}
lateinit var database: Database
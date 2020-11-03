@file:Suppress("EXPERIMENTAL_API_USAGE")
package moe.itsusinn.mychat.sql

import org.ktorm.database.Database
import moe.itsusinn.mychat.appConfig
import moe.itsusinn.mychat.sql.data.Comments
import moe.itsusinn.mychat.sql.data.Posts
import moe.itsusinn.mychat.sql.data.User
import moe.itsusinn.mychat.sql.data.Users
import org.ktorm.entity.sequenceOf

val database by lazy {
    val address = appConfig.property("mysql.address").getString()
    val port = appConfig.property("mysql.port").getString()
    val name = appConfig.property("mysql.database").getString()
    val user = appConfig.property("mysql.user").getString()
    val password = appConfig.property("mysql.password").getString()
    Database.connect("jdbc:mysql://$address:$port/$name?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
            driver = "com.mysql.cj.jdbc.Driver",
            user = user,
            password = password)
}

val Database.comments
    get() = this.sequenceOf(Comments)
val Database.users
    get() = this.sequenceOf(Users)
val Database.posts
    get() = this.sequenceOf(Posts)
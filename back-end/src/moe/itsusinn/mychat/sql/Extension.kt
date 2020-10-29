package moe.itsusinn.mychat.sql

import moe.itsusinn.mychat.sql.data.Comments
import moe.itsusinn.mychat.sql.data.Users
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf

val Database.comments
    get() = this.sequenceOf(Comments)
val Database.users
    get() = this.sequenceOf(Users)
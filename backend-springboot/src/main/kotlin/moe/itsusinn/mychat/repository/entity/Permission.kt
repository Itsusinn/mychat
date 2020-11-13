package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface Permission : Entity<Permission> {
    companion object : Entity.Factory<Post>()

    var permissionID: Long
    var url: String
    var name: String
    var description: String
}

object Permissions : Table<Permission>("permission") {
    var permissionID = long("permission_id").primaryKey().bindTo { it.permissionID }
    var url = varchar("url").bindTo { it.url }
    var name = varchar("name").bindTo { it.name }
    var description = varchar("description").bindTo { it.description }
}

val Database.permissions get() = this.sequenceOf(Permissions)
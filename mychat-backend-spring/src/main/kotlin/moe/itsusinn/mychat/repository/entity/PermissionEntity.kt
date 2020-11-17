package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface PermissionEntity : Entity<PermissionEntity> {
    companion object : Entity.Factory<PostEntity>()

    var permissionID: Long
    var url: String
    var name: String
    var description: String
}

object PermissionTable : Table<PermissionEntity>("permission") {
    var permissionID = long("permission_id").primaryKey().bindTo { it.permissionID }
    var url = varchar("url").bindTo { it.url }
    var name = varchar("name").bindTo { it.name }
    var description = varchar("description").bindTo { it.description }
}

val Database.permissions get() = this.sequenceOf(PermissionTable)
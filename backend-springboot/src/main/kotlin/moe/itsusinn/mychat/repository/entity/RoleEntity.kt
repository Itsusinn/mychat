package moe.itsusinn.mychat.repository.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface RoleEntity : Entity<RoleEntity> {
    companion object : Entity.Factory<RoleEntity>()
    var roleID: Long
    var name: String
}

object RoleTable : Table<RoleEntity>("role") {
    val roleID = long("role_id").primaryKey().bindTo { it.roleID }
    val name = varchar("name").bindTo { it.name }
}

val Database.roles get() = this.sequenceOf(RoleTable)
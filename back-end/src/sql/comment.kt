package org.meowcat.sql

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Comments:Table<Nothing>("comments"){
    val id = int("id").primaryKey()
    val target = int("target")
    val nick = varchar("nick")
    val email = varchar("email")
    val content = varchar("content")
}
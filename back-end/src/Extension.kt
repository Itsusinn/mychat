package org.meowcat

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.meowcat.sqldata.Comments

val Database.comments
    get() = this.sequenceOf(Comments)
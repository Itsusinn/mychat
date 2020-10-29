package org.meowcat.extend

import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.meowcat.sqldata.Comments

val Database.comments
    get() = this.sequenceOf(Comments)





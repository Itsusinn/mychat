package org.meowcat.route.jwt

import io.ktor.auth.*

data class User(
        val uid:Int,
        val account: String,
) : Principal
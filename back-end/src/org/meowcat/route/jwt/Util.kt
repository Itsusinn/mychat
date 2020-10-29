package org.meowcat.route.jwt

import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

val ApplicationCall.user get() = authentication.principal<User>()

val testUser = User(-1, "Test")
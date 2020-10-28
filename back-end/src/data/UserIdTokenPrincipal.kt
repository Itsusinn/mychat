package org.meowcat.data

import io.ktor.auth.*

/**
 * Represents a simple user's principal identified by [name]
 * @property name of user
 */
data class UserIdTokenPrincipal(val name: String,val token:String) : Principal
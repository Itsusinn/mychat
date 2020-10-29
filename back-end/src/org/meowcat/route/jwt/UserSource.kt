package org.meowcat.route.jwt

import io.ktor.auth.*

interface UserSource {

    fun findUserByUid(uid: Int): User

    fun findUserByCredentials(credential: UserPasswordCredential): User

}
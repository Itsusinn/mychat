package org.meowcat.route.jwt

import io.ktor.auth.*

class UserSourceImpl : UserSource {

    override fun findUserByUid(uid: Int): User = users.getValue(uid)

    override fun findUserByCredentials(credential: UserPasswordCredential): User = testUser

    private val users = listOf(testUser).associateBy(User::uid)

}
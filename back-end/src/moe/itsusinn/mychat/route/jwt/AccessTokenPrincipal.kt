package moe.itsusinn.mychat.route.jwt

import io.ktor.auth.*

data class AccessTokenPrincipal(val uid: Int) : Principal
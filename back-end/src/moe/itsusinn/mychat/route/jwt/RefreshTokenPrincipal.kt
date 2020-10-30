package moe.itsusinn.mychat.route.jwt

import io.ktor.auth.*

data class RefreshTokenPrincipal(val uid: Int) : Principal
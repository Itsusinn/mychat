package moe.itsusinn.mychat.route.jwt

import io.ktor.auth.*

data class RefreshTokenCredential(val uid: Int):Credential
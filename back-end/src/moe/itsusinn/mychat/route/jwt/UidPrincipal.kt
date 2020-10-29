package moe.itsusinn.mychat.route.jwt

import io.ktor.auth.*

data class UidPrincipal(
        val uid:Int,
) : Principal
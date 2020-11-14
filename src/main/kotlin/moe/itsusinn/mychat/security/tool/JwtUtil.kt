package moe.itsusinn.mychat.security.tool

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.security.core.AuthenticationException
import java.io.IOException

val objectKtMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

fun generateToken(uid: Long, uuid: String, roles: List<String>): String {
    val tokenData =
        TokenData(uid, uuid, System.currentTimeMillis(), roles)
    return objectKtMapper.writeValueAsString(tokenData).encodeBase64ToString()
}

fun generateToken(uid: Long, uuid: String, roles: String): String {
    return generateToken(uid, uuid, roles.split(":"))
}

fun parseToken(rawBase64Token: String): TokenData {
    return try {
        objectKtMapper.readValue(rawBase64Token.decodeBase64())
    } catch (e: IOException) {
        throw TokenParseException(e)
    }
}

class TokenParseException(e: Throwable) : AuthenticationException("Cannot Parse Token", e)

data class TokenData(
    val uid: Long,
    val uuid: String,
    val created: Long,
    val roles: List<String>
)

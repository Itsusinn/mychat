package moe.itsusinn.mychat.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*


val objectKtMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

const val ExpirationTime = 1000_60_30L

val Secret = "A very safe secret".toByteArray()

val TokenParser: JwtParser = Jwts
    .parserBuilder()
    .setSigningKey(Keys.hmacShaKeyFor(Secret))
    .build()

fun generateToken(claimsMap: Map<String, Any>): String {
    return Jwts.builder()
        .setClaims(claimsMap)
        .setExpiration(generateExpirationDate(ExpirationTime))
        .signWith(Keys.hmacShaKeyFor(Secret), SignatureAlgorithm.HS512) //采用什么算法是可以自己选择的，不一定非要采用HS512
        .compact()
}

fun generateToken(vararg claimPairs: Pair<String, Any>): String {
    val claimsMap = HashMap<String, Any>()
    claimPairs.forEach {
        claimsMap[it.first] = it.second
    }
    return generateToken(claimsMap)
}


fun getClaimsFromToken(token: String): Claims? =
    try {
        TokenParser
            .parseClaimsJws(token)
            .body
    } catch (e: Exception) {
        null
    }

fun generateExpirationDate(during: Long): Date {
    return Date(System.currentTimeMillis() + during)
}

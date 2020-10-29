@file:Suppress("EXPERIMENTAL_API_USAGE")
package moe.itsusinn.mychat.route.jwt

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import moe.itsusinn.mychat.extend.appConfig
import java.util.*

object JwtConfig {

    //密匙
    private val secret = appConfig.property("jwt.secret").toString()
    //签发者
    private val issuer = appConfig.property("jwt.issuer").toString()

    private const val validityInMs = 36_000_00 * 24 * 15// 15 days
    private val algorithm = Algorithm.HMAC512(secret)

    //解密用JWT
    val verifier: JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()

    /**
     * 签发Token
     */
    fun makeToken(user: UidPrincipal): String = JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("uid", user.uid)
            .withExpiresAt(getExpiration())
            .sign(algorithm)

    /**
     * 计算过期时间
     * 基于当前时间+有效期
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
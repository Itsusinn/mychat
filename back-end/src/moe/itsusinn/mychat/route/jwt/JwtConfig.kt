@file:Suppress("EXPERIMENTAL_API_USAGE")
package moe.itsusinn.mychat.route.jwt

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import moe.itsusinn.mychat.appConfig
import java.util.*

object JwtConfig {

    //密匙
    private val secret = appConfig.property("jwt.secret").toString()
    //签发者
    private val issuer = appConfig.property("jwt.issuer").toString()

    private const val validityInMs = 36_000_00 * 10 // 10 hours

    private val algorithm = Algorithm.HMAC512(secret)

    //解密用JWT
    val verifier: JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()

    /**
     * 签发AccessToken
     */
    fun makeAccessToken(refreshTokenCredential: RefreshTokenCredential): String = JWT.create()
            .withSubject("AccessTokenMaker")
            .withIssuer(issuer)
            .withClaim("uid",refreshTokenCredential.uid.toString())
            .withAudience(refreshTokenCredential.uid.toString())
            .withExpiresAt(getExpiration())
            .sign(algorithm)

    /**
     * 签发RefreshToken
     */
    fun makeRefreshToken(uid:Int):String = JWT.create()
            .withSubject("RefreshTokenMaker")
            .withIssuer(issuer)
            .withClaim("uid",uid.toString())
            .withAudience(uid.toString())
            .withExpiresAt(getExpiration())
            .sign(algorithm)

    /**
     * 计算过期时间
     * 基于当前时间+有效期
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
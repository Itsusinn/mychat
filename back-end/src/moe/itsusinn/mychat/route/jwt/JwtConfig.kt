@file:Suppress("EXPERIMENTAL_API_USAGE")
package moe.itsusinn.mychat.route.jwt

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import moe.itsusinn.mychat.appConfig
import java.util.*
/**
 * @property secret 密匙
 * @property issuer 签发者
 * @property verifier JWT验证器
 */
object JwtConfig {

    private val secret  = appConfig.property("jwt.secret").getString()
    private val issuer  = appConfig.property("jwt.issuer").getString()
    private const val validityInMs = Long.MAX_VALUE // 永久时效

    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()

    /**
     * 签发Token
     */
    fun makeToken(uid: Int,uuid: UUID): String = JWT.create()
            .withSubject("MyChat")
            .withIssuer(issuer)
            .withJWTId(uuid.toString())
            .withClaim("uid",uid.toString())
            .withExpiresAt(Date(validityInMs))
            .sign(algorithm)

}
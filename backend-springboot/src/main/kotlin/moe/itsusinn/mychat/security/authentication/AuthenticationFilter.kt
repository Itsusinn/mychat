package moe.itsusinn.mychat.security.authentication

import io.jsonwebtoken.io.IOException
import moe.itsusinn.mychat.models.UserLoginRequest
import moe.itsusinn.mychat.security.generateToken
import moe.itsusinn.mychat.security.objectKtMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthenticationFilter private constructor() : UsernamePasswordAuthenticationFilter() {

    constructor(authenticationManager: AuthenticationManager) : this() {
        this.authenticationManager = authenticationManager
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse
    ): Authentication {
        val userLogin = objectKtMapper.readValue(req.inputStream, UserLoginRequest::class.java)
        val credential = UsernamePasswordAuthenticationToken(
            userLogin.username,
            userLogin.password
        )
        return authenticationManager.authenticate(credential)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest?, res: HttpServletResponse, chain: FilterChain?,
        auth: Authentication
    ) {

        val token = generateToken("uuid" to UUID.randomUUID().toString())
        res.addHeader("token", token)
    }
}
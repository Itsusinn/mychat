package moe.itsusinn.mychat.configuration

import moe.itsusinn.mychat.security.atri.AtriAuthenticationProvider
import moe.itsusinn.mychat.services.MyValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


const val SignUpUrl = "/user/signup"

@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: MyValidator

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder


    override fun configure(auth: AuthenticationManagerBuilder) {

    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    //configure all the requests' rule
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.cors().and().csrf().disable()
            .authenticationProvider(AtriAuthenticationProvider(MyValidator()))
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, SignUpUrl).permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

}
package moe.itsusinn.mychat.security

import moe.itsusinn.mychat.services.ApplicationUserDetailsService
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


object SecurityConst {
    const val SignUpUrl = "/user/signup"
    const val Key = "&E)H@McQfTjWnZr4u7x!z%C*F-JaNdRgUkXp2s5v8y/B?D(G+KbPeShVmYq3t6w9z\$C&F)H@McQfTjWnZr4u7x!A%D*G-KaNdRgUkXp2s5v8y/B?E(H+MbQeShVmYq3t"
    const val HeaderName = "Authorization"
    const val ExpirationTime = 1000L*60*30
}

@EnableWebSecurity
class SecurityConfiguration(

    val userDetailsService: ApplicationUserDetailsService,
    val bCryptPasswordEncoder: BCryptPasswordEncoder

) : WebSecurityConfigurerAdapter() {

    override fun configure(httpSecurity:HttpSecurity){
        httpSecurity.cors().and().csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST,SecurityConst.SignUpUrl).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(AuthenticationFilter(authenticationManager()))
            .addFilter(AuthorizationFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    fun corsConfigurationSource(): CorsConfigurationSource =
        UrlBasedCorsConfigurationSource().also {
            it.registerCorsConfiguration(
                "/**",
                CorsConfiguration()
                .applyPermitDefaultValues())
        }

    override fun configure(auth:AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
    }



}
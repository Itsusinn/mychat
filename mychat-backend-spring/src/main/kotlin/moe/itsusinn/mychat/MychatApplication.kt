package moe.itsusinn.mychat

import com.spring4all.swagger.EnableSwagger2Doc
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableSwagger2Doc
@SpringBootApplication
class MychatApplication{
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}

fun main(args: Array<String>) {
    runApplication<MychatApplication>(*args)
}

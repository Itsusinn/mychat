package moe.itsusinn.mychat.configuration

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.support.http.StatViewServlet
import moe.itsusinn.mychat.logger
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DruidConfiguration {

    private val logger = logger()

    @Bean
    @ConfigurationProperties("spring.datasource")
    fun druidDataSource(): DataSource {
        return DruidDataSource()
    }

    @Bean
    fun druidStatViewServlet(): ServletRegistrationBean<*>? {

        val registrationBean: ServletRegistrationBean<*> =
            ServletRegistrationBean(StatViewServlet(), "/druid/*")

        registrationBean.apply {
            addInitParameter("allow", "127.0.0.1") // IP白名单 (没有配置或者为空，则允许所有访问)
            addInitParameter("deny", "") // IP黑名单 (存在共同时，deny优先于allow)
            addInitParameter("loginUsername", "root")
            addInitParameter("loginPassword", "root")
            addInitParameter("resetEnable", "false")
        }
        return registrationBean
    }
}
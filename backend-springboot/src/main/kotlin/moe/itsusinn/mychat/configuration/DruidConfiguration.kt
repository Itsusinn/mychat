package moe.itsusinn.mychat.configuration

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DruidConfiguration {

    private val logger: Logger = LoggerFactory.getLogger(DruidConfiguration::class.java)

    @Bean
    @ConfigurationProperties("spring.datasource")
    fun druidDataSource(): DataSource {
        return DruidDataSource()
    }

    @Bean
    fun druidWebStatViewFilter(): FilterRegistrationBean<*>? {
        val registrationBean: FilterRegistrationBean<*> = FilterRegistrationBean(WebStatFilter())
        registrationBean.addInitParameter("urlPatterns", "/*")
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
        return registrationBean
    }

    @Bean
    fun druidStatViewServlet(): ServletRegistrationBean<*>? {
        val registrationBean: ServletRegistrationBean<*> = ServletRegistrationBean(StatViewServlet(), "/druid/*")
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
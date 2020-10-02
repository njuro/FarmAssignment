package cz.cleverfarm.interview.farmassignment.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class TimeZoneConfiguration {

    /** Sets default application-wide timezone to be used when generating timestamps. */
    @Bean
    fun timeZone(): TimeZone? {
        val defaultTimeZone = TimeZone.getTimeZone("UTC")
        TimeZone.setDefault(defaultTimeZone)
        return defaultTimeZone
    }
}

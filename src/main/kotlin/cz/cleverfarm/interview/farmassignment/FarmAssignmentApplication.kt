package cz.cleverfarm.interview.farmassignment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@SpringBootApplication
class FarmAssignmentApplication

fun main(args: Array<String>) {
    runApplication<FarmAssignmentApplication>(*args)
}

@Configuration
class TimeZoneConfiguration {

    @Bean
    fun timeZone(): TimeZone? {
        val defaultTimeZone = TimeZone.getTimeZone("UTC")
        TimeZone.setDefault(defaultTimeZone)
        return defaultTimeZone
    }
}

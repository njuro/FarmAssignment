package cz.cleverfarm.interview.farmassignment.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .defaultSuccessUrl("/swagger-ui/index.html", true)
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .and()
            .csrf()
            .disable()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService? {
        val user: UserDetails = User.builder()
            .passwordEncoder { passwordEncoder().encode(it) }
            .username("user")
            .password("password")
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
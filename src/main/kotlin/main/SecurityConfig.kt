package main

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import services.PrincipalDetailService
import services.PrincipalService

@Configuration
@EnableWebSecurity
class SecurityConfig(@Autowired private val managerBeans: ManagerBeans) {
    @Bean
    fun userDetailsService(): PrincipalService {
        return PrincipalService(managerBeans.jdbcTemplate(), bCryptPasswordEncoder())
    }

    @Bean
    fun principalDetailService(): UserDetailsService {
        return PrincipalDetailService(managerBeans.jdbcTemplate())
    }

    @Bean
    fun authManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(principalDetailService())
        provider.setPasswordEncoder(bCryptPasswordEncoder())
        //return authConfig.authenticationManager
        return ProviderManager(provider)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("/registration").anonymous()
            .requestMatchers("/login").permitAll()
            //.anyRequest().authenticated()
            .anyRequest().permitAll()
        return httpSecurity.build()
    }
}
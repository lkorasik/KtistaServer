package com.ktinsta.server.config

import com.ktinsta.server.security.filters.JWTAuthenticationFilter
import com.ktinsta.server.security.filters.JWTLoginFilter
import com.ktinsta.server.service.AppUserDetailsService
import com.ktinsta.server.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig(val userDetailsService: AppUserDetailsService, val userService: UserService) : WebSecurityConfigurerAdapter() {

    /*
    Определяет какие url пути должны быть защищенны.
    Также накладывает спец фильры на запросы.

    Запросы не прошедшие проверку подлинности (или не были разрешенны)
    фильтруются JWTAuthenticationFilter
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                JWTLoginFilter("/api/auth/login", authenticationManager(), userService),
                UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(
                JWTAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java)

    }

    /*
    Устанавливает UserDetailsService и указывает применяемый кодировщик пароля
     */
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userDetailsService)
            .passwordEncoder(BCryptPasswordEncoder())
    }
}
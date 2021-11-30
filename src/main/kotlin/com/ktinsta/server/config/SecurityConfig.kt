package com.ktinsta.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/user/**").permitAll()
            .antMatchers("/login*").permitAll()
            .anyRequest().authenticated()
            .and()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("commonUser").password(passwordEncoder().encode("user123"))
            .roles("USER")
            .and()
            .withUser("adminUser").password(passwordEncoder().encode("admin2021"))
            .roles("ADMIN")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


}
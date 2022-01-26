package com.ktinsta.server.security.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.ktinsta.server.security.AccountCredentials
import com.ktinsta.server.security.service.TokenAuthenticationService
import com.ktinsta.server.service.AppUserDetailsService
import com.ktinsta.server.service.UserService
import io.jsonwebtoken.SignatureException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.naming.AuthenticationException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class JWTLoginFilter(url: String, authManager: AuthenticationManager, val userService: UserService)
    :AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse): Authentication {
        // Чтение учетных данных из запроса
        val credentials = ObjectMapper()
            .readValue(request.inputStream, AccountCredentials::class.java)

        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                credentials.username,
                credentials.password,
                emptyList()
            )
        )
    }

    /*
    Добавление токена аутентификации в заголовок ответа
     */
    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        TokenAuthenticationService.addAuthentication(response, authResult.name, userService.retrieveUserData(authResult.name)!!.id)
    }

}
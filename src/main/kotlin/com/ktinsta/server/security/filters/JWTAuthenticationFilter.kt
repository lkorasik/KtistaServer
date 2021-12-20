package com.ktinsta.server.security.filters

import com.ktinsta.server.security.service.TokenAuthenticationService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws

class JWTAuthenticationFilter : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val authentication = TokenAuthenticationService.getAuthentication(request as HttpServletRequest)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}
package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.constants.ResponseConstants
import com.ktinsta.server.helpers.objects.RegistrationVO
import com.ktinsta.server.security.AccountCredentials
import com.ktinsta.server.service.UserServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthController(val userService: UserServiceImpl, val userAssembler: UserAssembler) {
    @PostMapping("/registration")
    fun registration(@Valid @RequestBody userDetails: RegistrationVO, response: HttpServletResponse): ResponseEntity<Any> {
        if(!userService.isValid(userDetails))
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        userService.attemptRegistration(userDetails)

        return ResponseEntity.ok(ResponseConstants.SUCCESS.value)
    }

    /**
     * Stub for swagger
     */
    @PostMapping("/login")
    fun login(@RequestBody user: AccountCredentials) {}
}
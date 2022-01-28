package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.helpers.objects.RegistrationVO
import com.ktinsta.server.service.UserServiceImpl
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
            ResponseEntity.badRequest()

        val user = userService.attemptRegistration(userDetails)

        return ResponseEntity.ok(userAssembler.toAuthResponseVO(user))
    }
}
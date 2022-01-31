package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.constants.ResponseConstants
import com.ktinsta.server.helpers.objects.RegistrationVO
import com.ktinsta.server.security.AccountCredentials
import com.ktinsta.server.service.UserServiceImpl
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
@Api(tags = ["Authorization"], description = "This controller handles registration and authorization.")
class AuthController(val userService: UserServiceImpl, val userAssembler: UserAssembler) {

    @PostMapping("/registration")
    @ApiOperation(value = "Create new account.")
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
    @ApiOperation(value = "Login and get JWT. use this JWT to access other methods.")
    fun login(@RequestBody user: AccountCredentials) {}
}
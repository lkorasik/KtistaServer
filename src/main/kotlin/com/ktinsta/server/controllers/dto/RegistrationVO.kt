package com.ktinsta.server.controllers.dto

import com.ktinsta.server.validate.annotations.Email
import com.ktinsta.server.validate.annotations.Password
import com.ktinsta.server.validate.annotations.Username

data class RegistrationVO(
    @Username
    val username: String,
    @Password
    val password: String,
    @Email
    val email: String
)
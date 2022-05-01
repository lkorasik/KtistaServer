package com.ktinsta.server.controllers.dto

import com.ktinsta.server.validate.annotations.ValidateEmail
import com.ktinsta.server.validate.annotations.ValidatePassword
import com.ktinsta.server.validate.annotations.ValidateUsername

data class RegistrationDTO(
    @ValidateUsername
    val username: String,
    @ValidatePassword
    val password: String,
    @ValidateEmail
    val email: String
)

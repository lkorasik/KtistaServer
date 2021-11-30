package com.ktinsta.server.helpers.objects

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

data class LoginVO(
    val username: String,
    val password: String
)


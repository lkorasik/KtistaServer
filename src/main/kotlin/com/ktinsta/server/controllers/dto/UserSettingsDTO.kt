package com.ktinsta.server.controllers.dto

data class UserSettingsDTO(
    val avatar: ByteArray?,
    val email: String,
    val username: String
)

package com.ktinsta.server.controllers.dto

data class ShortUserDTO(
    val avatar: ByteArray? = null,
    val username: String,
)
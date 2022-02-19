package com.ktinsta.server.controllers.dto

data class FullUserDTO(
    val avatar: ByteArray? = null,
    val username: String,
    val followersCount: Int,
    val followingsCount: Int
)

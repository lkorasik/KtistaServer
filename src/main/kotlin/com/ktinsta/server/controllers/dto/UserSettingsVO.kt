package com.ktinsta.server.controllers.dto

data class UserSettingsVO(
    val avatar: ByteArray?,
    val email: String,
    val nickname: String
)
package com.ktinsta.server.helpers.objects

data class SettingsDTO(
    val avatar: ByteArray?,
    val email: String,
    val nickname: String
)
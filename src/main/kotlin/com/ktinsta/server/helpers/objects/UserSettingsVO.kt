package com.ktinsta.server.helpers.objects

data class UserSettingsVO(
    val avatar: ByteArray?,
    val email: String,
    val nickname: String
)
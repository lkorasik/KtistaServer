package com.ktinsta.server.helpers.objects

data class UserSettingsVO(
    val id: Long,
    val avatar: ByteArray?,
    val email: String?,
    val nickname: String?
)
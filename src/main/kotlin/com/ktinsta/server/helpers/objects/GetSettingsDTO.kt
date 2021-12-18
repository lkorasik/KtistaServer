package com.ktinsta.server.helpers.objects

data class GetSettingsDTO(
    val id: Long,
    val avatar: ByteArray?,
    val email: String?,
    val nickname: String?
)
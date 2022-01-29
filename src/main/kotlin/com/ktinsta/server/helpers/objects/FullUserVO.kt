package com.ktinsta.server.helpers.objects

data class FullUserVO(
    val image: ByteArray? = null,
    val username: String,
    val followers: Int,
    val followings: Int
)
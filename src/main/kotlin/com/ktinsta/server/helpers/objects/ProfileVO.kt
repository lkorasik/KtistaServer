package com.ktinsta.server.helpers.objects

data class ProfileVO(
    val image: ByteArray,
    val username: String,
    val followers: Int,
    val followings: Int
)
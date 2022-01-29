package com.ktinsta.server.helpers.objects

data class UserVO(
    val image: ByteArray? = null,
    val username: String,
    val followers: Int,
    val followings: Int
)
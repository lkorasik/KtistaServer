package com.ktinsta.server.helpers.objects

data class UserVO(
    val image: ByteArray,
    val username: String,
    val followers: Int,
    val followings: Int
)

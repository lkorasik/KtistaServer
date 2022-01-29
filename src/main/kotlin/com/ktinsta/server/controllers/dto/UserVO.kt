package com.ktinsta.server.controllers.dto

data class UserVO(
    val image: ByteArray? = null,
    val username: String,
    val followers: Int,
    val followings: Int
)
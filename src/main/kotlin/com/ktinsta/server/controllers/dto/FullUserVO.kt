package com.ktinsta.server.controllers.dto

data class FullUserVO(
    val image: ByteArray? = null,
    val username: String,
    val followers: Int,
    val followings: Int
)
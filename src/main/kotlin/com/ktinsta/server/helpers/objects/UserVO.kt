package com.ktinsta.server.helpers.objects

data class UserVO(
    val id: Long,
    val email: String,
    val userStatus: String,
    val createdAt: String,
)

data class UserListVO(
    val users: List<UserVO>
)

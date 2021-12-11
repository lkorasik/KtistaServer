package com.ktinsta.server.components

import com.ktinsta.server.helpers.objects.AuthResponseVO
import com.ktinsta.server.helpers.objects.LoginVO
import com.ktinsta.server.helpers.objects.UserListVO
import com.ktinsta.server.helpers.objects.UserVO
import com.ktinsta.server.model.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toUserVO(user: User): UserVO {
        return UserVO(
            id = user.id,
            email = user.email,
            userStatus = user.status,
            createdAt = user.createdAt.toString()
        )
    }

    fun toAuthResponseVO(user: User): AuthResponseVO = AuthResponseVO(user.id)

    fun toUserListVO(users: List<User>): UserListVO {
        val userListVO = users.map { toUserVO(it) }
        return UserListVO(userListVO)
    }
}
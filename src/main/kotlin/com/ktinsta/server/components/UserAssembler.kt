package com.ktinsta.server.components

import com.ktinsta.server.helpers.objects.AuthResponseVO
import com.ktinsta.server.helpers.objects.ProfileVO
import com.ktinsta.server.helpers.objects.UserListVO
import com.ktinsta.server.helpers.objects.UserVO
import com.ktinsta.server.model.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toProfileVO(user: User): ProfileVO {
        return ProfileVO(
            image = byteArrayOf(1),
            username = user.username,
            followers = 0,
            followings = 0
        )
    }

    fun toAuthResponseVO(user: User): AuthResponseVO = AuthResponseVO(user.id)

//    fun toUserListVO(users: List<User>): UserListVO {
//        val userListVO = users.map { toProfileVO(it) }
//        return UserListVO(userListVO)
//    }
}
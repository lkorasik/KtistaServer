package com.ktinsta.server.components

import com.ktinsta.server.helpers.objects.AuthResponseVO
import com.ktinsta.server.helpers.objects.UserVO
import com.ktinsta.server.model.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toUserVO(user: User): UserVO {
        return UserVO(
            image = byteArrayOf(1), //TODO: Дописать код для отправки аватарки
            username = user.username,
            followers = 0, //TODO: Дописать код для подсчета числа подписок и подписчиков
            followings = 0
        )
    }

    fun toAuthResponseVO(user: User): AuthResponseVO = AuthResponseVO(user.id)

//    fun toUserListVO(users: List<User>): UserListVO {
//        val userListVO = users.map { toUserVO(it) }
//        return UserListVO(userListVO)
//    }
}
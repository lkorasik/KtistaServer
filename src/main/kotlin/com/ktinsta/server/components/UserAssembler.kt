package com.ktinsta.server.components

import com.ktinsta.server.controllers.dto.UserVO
import com.ktinsta.server.storage.model.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toUserVO(user: User): UserVO {
        return UserVO(
            image = user.avatar?.data,
            username = user.username,
            followers = 0, //TODO: Дописать код для подсчета числа подписок и подписчиков
            followings = 0
        )
    }

//    fun toUserListVO(users: List<User>): UserListVO {
//        val userListVO = users.map { toUserVO(it) }
//        return UserListVO(userListVO)
//    }
}
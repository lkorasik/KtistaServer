package com.ktinsta.server.components

import com.ktinsta.server.helpers.objects.FullUserVO
import com.ktinsta.server.helpers.objects.ShortUserVO
import com.ktinsta.server.model.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toFullUserVO(user: User): FullUserVO {
        return FullUserVO(
            image = user.avatar?.data,
            username = user.username,
            followers = 0, //TODO: Дописать код для подсчета числа подписок и подписчиков
            followings = 0
        )
    }

    fun toShortUserVO(user: User): ShortUserVO {
        return  ShortUserVO(
            image = user.avatar?.data,
            username = user.username
        )
    }

//    fun toUserListVO(users: List<User>): UserListVO {
//        val userListVO = users.map { toUserVO(it) }
//        return UserListVO(userListVO)
//    }
}
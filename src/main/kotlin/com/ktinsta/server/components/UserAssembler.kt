package com.ktinsta.server.components

import com.ktinsta.server.controllers.dto.FullUserVO
import com.ktinsta.server.helpers.objects.ShortUserVO
import com.ktinsta.server.storage.model.FullUser
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toFullUserVO(fullUser: FullUser): FullUserVO {
        return FullUserVO(
            image = fullUser.avatar?.data,
            username = fullUser.username,
            followers = 0, //TODO: Дописать код для подсчета числа подписок и подписчиков
            followings = 0
        )
    }

    fun toShortUserVO(fullUser: FullUser): ShortUserVO {
        return  ShortUserVO(
            image = fullUser.avatar?.data,
            username = fullUser.username
        )
    }
}
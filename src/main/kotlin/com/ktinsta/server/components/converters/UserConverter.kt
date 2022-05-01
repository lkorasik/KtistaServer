package com.ktinsta.server.components.converters

import com.ktinsta.server.controllers.dto.ShortUserDTO
import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.FullUser
import org.springframework.stereotype.Component

@Component
class UserConverter {
    fun toShortUserVO(user: FullUser): ShortUserDTO {
        return  ShortUserDTO(
            avatar = user.avatar?.data,
            username = user.username
        )
    }

    fun toShortUserVO(user: BriefUser): ShortUserDTO {
        return  ShortUserDTO(
            avatar = user.avatar?.data,
            username = user.username
        )
    }
}

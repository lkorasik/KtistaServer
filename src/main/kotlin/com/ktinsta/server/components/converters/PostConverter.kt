package com.ktinsta.server.components.converters

import com.ktinsta.server.controllers.dto.ReturnPostDTO
import com.ktinsta.server.storage.model.Post
import org.springframework.stereotype.Component

@Component
class PostConverter(private val userConverter: UserConverter) {
    fun toPostVO(post: Post): ReturnPostDTO {
        return ReturnPostDTO(
            author = userConverter.toShortUserVO(post.author),
            text = post.text,
            image = post.image.data
        )
    }
}

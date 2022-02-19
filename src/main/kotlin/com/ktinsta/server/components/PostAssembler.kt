package com.ktinsta.server.components

import com.ktinsta.server.controllers.dto.ReturnPostDTO
import com.ktinsta.server.storage.model.Post
import org.springframework.stereotype.Component

@Component
class PostAssembler(private val userAssembler: UserAssembler) {
    fun toPostVO(post: Post): ReturnPostDTO {
        return ReturnPostDTO(
            author = userAssembler.toShortUserVO(post.author),
            text = post.text,
            image = post.image.data
        )
    }
}
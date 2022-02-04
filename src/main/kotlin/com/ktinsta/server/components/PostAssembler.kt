package com.ktinsta.server.components

import com.ktinsta.server.helpers.objects.ReturnPostVO
import com.ktinsta.server.storage.model.Post
import org.springframework.stereotype.Component

@Component
class PostAssembler(private val userAssembler: UserAssembler) {
    fun toPostVO(post: Post): ReturnPostVO {
        return ReturnPostVO(
            author = userAssembler.toShortUserVO(post.author),
            text = post.text,
            data = post.image.data
        )
    }
}
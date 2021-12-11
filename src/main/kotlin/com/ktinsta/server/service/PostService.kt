package com.ktinsta.server.service

import com.ktinsta.server.model.Image
import com.ktinsta.server.model.Post

interface PostService {
    fun create(postDetails: Post, imageDetails: Image)
}
package com.ktinsta.server.service

import com.ktinsta.server.storage.model.Post

interface PostService {
    fun create(postDetails: Post)
}
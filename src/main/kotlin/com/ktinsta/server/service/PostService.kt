package com.ktinsta.server.service

import com.ktinsta.server.model.Image
import com.ktinsta.server.model.Post
import com.ktinsta.server.model.User

interface PostService {
    fun create(postDetails: Post)
    fun getAllPosts(author: User): List<Post>?
}
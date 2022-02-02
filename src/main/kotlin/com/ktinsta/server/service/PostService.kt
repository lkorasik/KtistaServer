package com.ktinsta.server.service


import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.model.User

interface PostService {
    fun create(postDetails: Post)
    fun getAllPosts(author: User): List<Post>?
    fun getFeed(user: User): List<Post>
}
package com.ktinsta.server.service


import com.ktinsta.server.storage.model.BriefPost
import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.model.FullUser

interface PostService {
    fun create(postDetails: BriefPost)
    fun create(postDetails: Post)
    fun getAllPosts(author: User): List<Post>?
    fun getFeed(user: User): List<Post>
    fun getAllPosts(author: FullUser): List<Post>?
}
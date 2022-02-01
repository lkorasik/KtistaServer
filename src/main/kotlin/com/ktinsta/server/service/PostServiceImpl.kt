package com.ktinsta.server.service

import com.ktinsta.server.storage.model.BriefPost
import com.ktinsta.server.storage.model.FullUser
import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.repository.BriefPostRepository
import com.ktinsta.server.storage.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(val postRepository: PostRepository, val briefPostRepository: BriefPostRepository): PostService {
    override fun create(postDetails: BriefPost) {
        briefPostRepository.save(postDetails)
    }

    override fun create(postDetails: Post) {
        postRepository.save(postDetails)
    }

    override fun getAllPosts(author: FullUser): List<Post>? {
        return postRepository.findByAuthor(author)
    }
}
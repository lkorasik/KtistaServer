package com.ktinsta.server.service

import com.ktinsta.server.model.Image
import com.ktinsta.server.model.Post
import com.ktinsta.server.repository.ImageRepository
import com.ktinsta.server.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(val postRepository: PostRepository, val imageRepository: ImageRepository): PostService {
    override fun create(postDetails: Post, imageDetails: Image) {
        postRepository.save(postDetails)
        imageRepository.save(imageDetails)
    }
}
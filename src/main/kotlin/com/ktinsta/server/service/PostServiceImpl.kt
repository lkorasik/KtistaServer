package com.ktinsta.server.service

import com.ktinsta.server.storage.model.Image
import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.model.User
import com.ktinsta.server.storage.repository.ImageRepository
import com.ktinsta.server.storage.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(val postRepository: PostRepository): PostService {
    override fun create(postDetails: Post) {
        postRepository.save(postDetails)
    }

    override fun getAllPosts(author: User): List<Post>? {
        return postRepository.findByAuthor(author)
    }
}

@Service
class ImageService(val imageRepository: ImageRepository){
    fun create(image: Image){
        imageRepository.save(image)
    }
}
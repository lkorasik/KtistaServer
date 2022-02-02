package com.ktinsta.server.service

import com.ktinsta.server.storage.model.Image
import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.model.User
import com.ktinsta.server.storage.repository.ImageRepository
import com.ktinsta.server.storage.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(val postRepository: PostRepository, val followersService: FollowersService): PostService {
    override fun create(postDetails: Post) {
        postRepository.save(postDetails)
    }

    override fun getAllPosts(author: User): List<Post>? {
        return postRepository.findByAuthor(author)
    }

    override fun getFeed(user: User): List<Post> {
        val followings = followersService.getAllFollowings(user.id)

        val feed = mutableListOf<Post>()
        for(following in followings){
            val posts = postRepository.findByAuthor(following.follower)
            if (posts != null) {
                feed.addAll(posts)
            }
        }

        return feed.sortedBy { it.createdAt }
    }
}

@Service
class ImageService(val imageRepository: ImageRepository){
    fun create(image: Image){
        imageRepository.save(image)
    }
}
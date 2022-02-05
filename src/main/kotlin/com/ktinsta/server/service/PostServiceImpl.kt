package com.ktinsta.server.service

import com.ktinsta.server.storage.model.BriefPost
import com.ktinsta.server.storage.model.FullUser
import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.repository.BriefPostRepository
import com.ktinsta.server.storage.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(val postRepository: PostRepository, val briefPostRepository: BriefPostRepository, val followersService: FollowersService): PostService {
    override fun create(postDetails: BriefPost) {
        briefPostRepository.save(postDetails)
    }

    override fun getAllPosts(author: FullUser): List<Post>? {
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
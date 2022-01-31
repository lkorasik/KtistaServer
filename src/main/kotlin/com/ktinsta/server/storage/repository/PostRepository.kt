package com.ktinsta.server.storage.repository

import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long>{
    fun findByAuthor(author: User): List<Post>?
}
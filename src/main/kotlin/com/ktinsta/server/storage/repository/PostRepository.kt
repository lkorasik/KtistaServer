package com.ktinsta.server.storage.repository

import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.FullUser
import com.ktinsta.server.storage.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long>{
    fun findByAuthor(author: FullUser): List<Post>?
    fun findByAuthor(author: BriefUser): List<Post>?
}
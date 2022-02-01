package com.ktinsta.server.storage.repository

import com.ktinsta.server.storage.model.BriefPost
import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.Post
import com.ktinsta.server.storage.model.FullUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long>{
    fun findByAuthor(author: FullUser): List<Post>?
}

@Repository
interface BriefPostRepository : JpaRepository<BriefPost, Long>{
    fun findByAuthor(author: BriefUser): List<Post>?
}
package com.ktinsta.server.repository

import com.ktinsta.server.model.Image
import com.ktinsta.server.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long>

@Repository
interface ImageRepository: JpaRepository<Image, Long>
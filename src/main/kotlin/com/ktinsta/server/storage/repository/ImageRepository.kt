package com.ktinsta.server.storage.repository

import com.ktinsta.server.storage.model.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository: JpaRepository<Image, Long>
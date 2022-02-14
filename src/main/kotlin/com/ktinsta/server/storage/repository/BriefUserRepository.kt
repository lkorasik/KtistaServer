package com.ktinsta.server.storage.repository

import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.FullUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BriefUserRepository: JpaRepository<BriefUser, Long> {
    fun findByUsername(username: String): BriefUser?
}
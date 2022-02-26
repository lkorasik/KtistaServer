package com.ktinsta.server.storage.repository

import com.ktinsta.server.storage.model.FullUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FullUserRepository : JpaRepository<FullUser, Long> {
    fun findByUsername(username: String): FullUser?
    fun findByEmail(email: String): FullUser?
}
package com.ktinsta.server.storage.repository

import com.ktinsta.server.storage.model.Followers
import com.ktinsta.server.storage.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface FollowersRepository: JpaRepository<Followers, Long>{
    fun findByUser(user: User): List<Followers>
}
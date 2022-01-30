package com.ktinsta.server.repository

import com.ktinsta.server.model.Followers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface FollowersRepository: JpaRepository<Followers, Long>
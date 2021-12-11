package com.ktinsta.server.repository

import com.ktinsta.server.model.Post
import com.ktinsta.server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?

}

@Repository
interface PostRepository: JpaRepository<Post, Long>{
}
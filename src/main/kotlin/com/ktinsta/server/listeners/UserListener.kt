package com.ktinsta.server.listeners

import com.ktinsta.server.model.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class UserListener {
    @PrePersist
    fun hashPassword(user: User) {
        user.password = BCryptPasswordEncoder().encode(user.password)
    }
}
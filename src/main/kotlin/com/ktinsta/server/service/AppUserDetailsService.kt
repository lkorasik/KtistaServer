package com.ktinsta.server.service

import com.ktinsta.server.storage.repository.FullUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.ArrayList
import kotlin.jvm.Throws

@Component
class AppUserDetailsService(val fullUserRepository: FullUserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = fullUserRepository.findByUsername(username) ?:
            throw UsernameNotFoundException("User with $username doesn't exist")
        return User(user.username, user.password, ArrayList())
    }
}
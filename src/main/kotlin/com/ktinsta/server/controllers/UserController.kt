package com.ktinsta.server.controllers

import com.ktinsta.server.model.User
import com.ktinsta.server.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class ArticleController(private val userRepository: UserRepository) {

    @GetMapping("/list")
    fun getAllUsers(): List<User> =
        userRepository.findAll()


    @PostMapping("/registration")
    fun createNewUser(@Valid @RequestBody user: User): User {
        return userRepository.save(user)
    }

    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable(value = "id") articleId: Long): ResponseEntity<User> {
        return userRepository.findById(articleId).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/user/{id}")
    fun updateUserById(@PathVariable(value = "id") userId: Long,
                       @Valid @RequestBody newUser: User): ResponseEntity<User> {

        return userRepository.findById(userId).map { existingArticle ->
            val updatedArticle: User = existingArticle
                .copy(email = newUser.email, username = newUser.username, password = newUser.password)
            ResponseEntity.ok().body(userRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/user/{id}")
    fun deleteUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<Void> {

        return userRepository.findById(userId).map { article  ->
            userRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}
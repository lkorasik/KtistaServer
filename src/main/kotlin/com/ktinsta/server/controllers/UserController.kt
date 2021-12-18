package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.helpers.objects.GetSettingsDTO
import com.ktinsta.server.helpers.objects.UserVO
import com.ktinsta.server.repository.UserRepository
import com.ktinsta.server.service.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(val userRepository: UserRepository, val userService: UserServiceImpl, val userAssembler: UserAssembler) {

    @GetMapping("/profile/{id}")
    fun getProfile(@PathVariable(value = "id") userId: Long): ResponseEntity<UserVO> {
        val user = userService.retrieveUserData(userId)
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping("/settings/{id}")
    fun setSettings(@PathVariable(value = "id") userId: Long): ResponseEntity<GetSettingsDTO>{
        return ResponseEntity.ok(userService.getSettings(userId))
    }
}
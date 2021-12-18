package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.helpers.objects.LoginVO
import com.ktinsta.server.helpers.objects.UserVO
import com.ktinsta.server.helpers.objects.RegistrationVO
import com.ktinsta.server.helpers.objects.SettingsAvatarDTO
import com.ktinsta.server.repository.UserRepository
import com.ktinsta.server.service.UserServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(val userRepository: UserRepository, val userService: UserServiceImpl, val userAssembler: UserAssembler) {

    @GetMapping("/profile/{id}")
    fun getProfile(@PathVariable(value = "id") userId: Long): ResponseEntity<UserVO> {
        val user = userService.retrieveUserData(userId)
        return ResponseEntity.ok(userAssembler.toUserVO(user))
        //return ResponseEntity.ok(userAssembler.toProfileVO(user))
    }

    @PostMapping("/settings/avatar")
    fun setAvatar(@Valid @RequestBody settings: SettingsAvatarDTO): ResponseEntity<Void> {
        val user = userService.retrieveUserData(settings.id)
        userService.setAvatar(user, settings.avatar)

        return ResponseEntity.ok().build()
    }
}
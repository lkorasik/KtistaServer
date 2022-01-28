package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.helpers.objects.UserSettingsVO
import com.ktinsta.server.helpers.objects.UserVO
import com.ktinsta.server.model.Image
import com.ktinsta.server.service.AvatarService
import com.ktinsta.server.service.ImageService
import com.ktinsta.server.service.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserServiceImpl, val userAssembler: UserAssembler, val avatarService: AvatarService, val imageService: ImageService) {

    @GetMapping("/profile/{id}")
    fun getProfile(@PathVariable(value = "id") userId: Long): ResponseEntity<UserVO> {
        var user = userService.retrieveUserData(userId)

        user.avatar?.let {
            val avatarThumbnail = avatarService.makeThumbnail(it.data)
            val avatar = Image(data = avatarThumbnail)
            user = user.copy(avatar = avatar)
        }

        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping("/settings/{id}")
    fun getSettings(@PathVariable(value = "id") userId: Long): ResponseEntity<UserSettingsVO>{
        return ResponseEntity.ok(userService.getSettings(userId))
    }

    @PostMapping("/settings/{id}")
    fun setSettings(@PathVariable(value = "id") userId: Long, @Valid @RequestBody userSettings: UserSettingsVO): ResponseEntity<Void>{
        userService.setSettings(userId, userSettings)
        return ResponseEntity.ok().build()
    }
}
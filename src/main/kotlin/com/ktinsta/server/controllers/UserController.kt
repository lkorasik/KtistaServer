package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.controllers.dto.UserSettingsVO
import com.ktinsta.server.controllers.dto.UserVO
import com.ktinsta.server.storage.model.Image
import com.ktinsta.server.security.service.TokenAuthenticationService
import com.ktinsta.server.service.AvatarService
import com.ktinsta.server.service.ImageService
import com.ktinsta.server.service.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserServiceImpl, val userAssembler: UserAssembler, val avatarService: AvatarService, val imageService: ImageService) {

    @GetMapping("/profile")
    fun getProfile(request: HttpServletRequest): ResponseEntity<UserVO> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        var user = userService.retrieveUserData(userId)

        user.avatar?.let {
            val avatarThumbnail = avatarService.makeThumbnail(it.data)
            val avatar = Image(data = avatarThumbnail)
            user = user.copy(avatar = avatar)
        }

        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping("/settings")
    fun getSettings(request: HttpServletRequest): ResponseEntity<UserSettingsVO>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        return ResponseEntity.ok(userService.getSettings(userId))
    }

    @PostMapping("/settings")
    fun setSettings(@Valid @RequestBody userSettings: UserSettingsVO, request: HttpServletRequest): ResponseEntity<Void>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        userService.setSettings(userId, userSettings)
        return ResponseEntity.ok().build()
    }
}
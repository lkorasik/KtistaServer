package com.ktinsta.server.controllers

import com.ktinsta.server.components.PostAssembler
import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.helpers.objects.UserSettingsVO
import com.ktinsta.server.helpers.objects.FullUserVO
import com.ktinsta.server.helpers.objects.ReturnPostVO
import com.ktinsta.server.model.Image
import com.ktinsta.server.security.service.TokenAuthenticationService
import com.ktinsta.server.service.AvatarService
import com.ktinsta.server.service.PostService
import com.ktinsta.server.service.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(
    val userService: UserServiceImpl,
    val userAssembler: UserAssembler,
    val avatarService: AvatarService,
    val postService: PostService,
    val postAssembler: PostAssembler
) {

    @GetMapping("/profile")
    fun getProfile(request: HttpServletRequest): ResponseEntity<FullUserVO> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        var user = userService.retrieveUserData(userId)

        user.avatar?.let {
            val avatarThumbnail = avatarService.makeThumbnail(it.data)
            val avatar = Image(data = avatarThumbnail)
            user = user.copy(avatar = avatar)
        }

        return ResponseEntity.ok(userAssembler.toFullUserVO(user))
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

    @GetMapping("/all-my-posts")
    fun getAllMyPosts(request: HttpServletRequest): ResponseEntity<List<ReturnPostVO>> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val user = userService.retrieveUserData(userId)
        val posts = postService.getAllPosts(user)

        return ResponseEntity.ok(posts?.map { postAssembler.toPostVO(it) })
    }
}
package com.ktinsta.server.controllers

import com.ktinsta.server.components.PostAssembler
import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.controllers.dto.UserSettingsVO
import com.ktinsta.server.helpers.objects.FollowingVO
import com.ktinsta.server.helpers.objects.ReturnPostVO
import com.ktinsta.server.helpers.objects.ShortUserVO
import com.ktinsta.server.security.service.TokenAuthenticationService
import com.ktinsta.server.service.*
import com.ktinsta.server.storage.model.Image
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
@Api(tags = ["User"], description = "This controller handles everything related to the user (except authorization).")
class UserController(
    val userService: UserServiceImpl,
    val userAssembler: UserAssembler,
    val avatarService: AvatarService,
    val followersService: FollowersService,
    val postService: PostService,
    val postAssembler: PostAssembler) {

    @GetMapping("/profile")
    @ApiOperation(value = "Get user information like username, avatar and etc.")
    fun getProfile(request: HttpServletRequest): ResponseEntity<ShortUserVO> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        var user = userService.retrieveUserData(userId)

        user.avatar?.let {
            val avatarThumbnail = avatarService.makeThumbnail(it.data)
            val avatar = Image(data = avatarThumbnail)
            user = user.copy(avatar = avatar)
        }

        return ResponseEntity.ok(userAssembler.toShortUserVO(user))
    }

    @GetMapping("/settings")
    @ApiOperation(value = "Get user's settings like username, avatar and etc.")
    fun getSettings(request: HttpServletRequest): ResponseEntity<UserSettingsVO>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        return ResponseEntity.ok(userService.getSettings(userId))
    }

    @PostMapping("/settings")
    @ApiOperation(value = "Set new user's settings.")
    fun setSettings(@Valid @RequestBody userSettings: UserSettingsVO, request: HttpServletRequest): ResponseEntity<Void>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        userService.setSettings(userId, userSettings)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/subscribe")
    fun subscribe(@Valid @RequestBody following: FollowingVO, request: HttpServletRequest): ResponseEntity<Void>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val username = userService.retrieveUserData(userId).username
        followersService.subscribe(username, following.username)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/unsubscribe")
    fun unsubscribe(@Valid @RequestBody following: FollowingVO, request: HttpServletRequest): ResponseEntity<Void> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val username = userService.retrieveUserData(userId).username
        followersService.unsubscribe(username, following.username)
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
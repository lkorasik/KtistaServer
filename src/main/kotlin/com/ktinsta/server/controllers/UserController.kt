package com.ktinsta.server.controllers

import com.ktinsta.server.components.converters.PostConverter
import com.ktinsta.server.components.converters.UserConverter
import com.ktinsta.server.controllers.dto.UserSettingsDTO
import com.ktinsta.server.controllers.dto.FollowingDTO
import com.ktinsta.server.controllers.dto.ReturnPostDTO
import com.ktinsta.server.controllers.dto.ShortUserDTO
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
    val userConverter: UserConverter,
    val avatarService: AvatarService,
    val followersService: FollowersService,
    val postService: PostService,
    val postConverter: PostConverter
) {

    @GetMapping("/profile")
    @ApiOperation(value = "Get user information like username, avatar and etc.")
    fun getProfile(request: HttpServletRequest): ResponseEntity<ShortUserDTO> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        var user = userService.retrieveFullUserData(userId)

        user.avatar?.let {
            val avatarThumbnail = avatarService.makeThumbnail(it.data)
            val avatar = Image(data = avatarThumbnail)
            user = user.copy(avatar = avatar)
        }

        return ResponseEntity.ok(userConverter.toShortUserVO(user))
    }

    @GetMapping("/settings")
    @ApiOperation(value = "Get user's settings like username, avatar and etc.")
    fun getSettings(request: HttpServletRequest): ResponseEntity<UserSettingsDTO>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        return ResponseEntity.ok(userService.getSettings(userId))
    }

    @PostMapping("/settings")
    @ApiOperation(value = "Set new user's settings.")
    fun setSettings(@Valid @RequestBody userSettings: UserSettingsDTO, request: HttpServletRequest): ResponseEntity<Void>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        userService.setSettings(userId, userSettings)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/subscribe")
    fun subscribe(@Valid @RequestBody following: FollowingDTO, request: HttpServletRequest): ResponseEntity<Void>{
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val username = userService.retrieveBriefUserData(userId)!!.username
        followersService.subscribe(username, following.username)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/unsubscribe")
    fun unsubscribe(@Valid @RequestBody following: FollowingDTO, request: HttpServletRequest): ResponseEntity<Void> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val username = userService.retrieveFullUserData(userId).username
        followersService.unsubscribe(username, following.username)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/all-my-posts")
    fun getAllMyPosts(request: HttpServletRequest): ResponseEntity<List<ReturnPostDTO>> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val user = userService.retrieveFullUserData(userId)
        val posts = postService.getAllPosts(user)

        return ResponseEntity.ok(posts?.map { postConverter.toPostVO(it) })
    }

    @GetMapping("/all-followings")
    fun getAllMyFollowings(request: HttpServletRequest): ResponseEntity<List<ShortUserDTO>> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val followings = followersService.getAllFollowings(userId)

        return ResponseEntity.ok(followings.map { userConverter.toShortUserVO(it.follower) })
    }

    @GetMapping("/feed")
    fun getFeed(request: HttpServletRequest): ResponseEntity<List<ReturnPostDTO>> {
        val userId = TokenAuthenticationService.getUserIdFromRequest(request)
        val user = userService.retrieveFullUserData(userId)
        val feed = postService.getFeed(user)

        return ResponseEntity.ok(feed.map { postConverter.toPostVO(it) })
    }
}

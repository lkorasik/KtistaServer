package com.ktinsta.server.controllers

import com.ktinsta.server.constants.ResponseConstants
import com.ktinsta.server.helpers.objects.CreatePostVO
import com.ktinsta.server.model.Image
import com.ktinsta.server.model.Post
import com.ktinsta.server.security.service.TokenAuthenticationService
import com.ktinsta.server.service.ImageService
import com.ktinsta.server.service.PostService
import com.ktinsta.server.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/post/")
class PostController(val postService: PostService, val userService: UserService, val imageRepository: ImageService){
    @PostMapping("/create")
    fun createPost(@Valid @RequestBody postDetails: CreatePostVO, response: HttpServletResponse, request: HttpServletRequest): ResponseEntity<Any> {
        val authorId = TokenAuthenticationService.getUserIdFromRequest(request)
        val author = userService.retrieveUserData(authorId)

        val image = Image(data = postDetails.data)
        imageRepository.create(image)

        val post = Post(author = author!!, text = postDetails.text, image = image)
        postService.create(post)

        return ResponseEntity.ok(ResponseConstants.SUCCESS.value)
    }
}
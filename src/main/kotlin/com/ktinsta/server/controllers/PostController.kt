package com.ktinsta.server.controllers

import com.ktinsta.server.constants.ResponseConstants
import com.ktinsta.server.controllers.dto.CreatePostDTO
import com.ktinsta.server.security.service.TokenAuthenticationService
import com.ktinsta.server.service.ImageService
import com.ktinsta.server.service.PostService
import com.ktinsta.server.service.UserService
import com.ktinsta.server.storage.model.BriefPost
import com.ktinsta.server.storage.model.Image
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
@Api(tags = ["Post"], description = "This controller handles everything related to the post.")
class PostController(val postService: PostService, val userService: UserService, val imageRepository: ImageService){

    @PostMapping("/create")
    @ApiOperation(value = "Create new post")
    fun createPost(@Valid @RequestBody postDetails: CreatePostDTO, response: HttpServletResponse, request: HttpServletRequest): ResponseEntity<Any> {
        val authorId = TokenAuthenticationService.getUserIdFromRequest(request)
        val author = userService.retrieveBriefUserData(authorId)

        val image = Image(data = postDetails.image)
        imageRepository.create(image)

        val post = BriefPost(author = author!!, text = postDetails.text, image = image)
        postService.create(post)

        return ResponseEntity.ok(ResponseConstants.SUCCESS.value)
    }
}

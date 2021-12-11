package com.ktinsta.server.controllers

import com.ktinsta.server.constants.ResponseConstants
import com.ktinsta.server.model.Image
import com.ktinsta.server.model.Post
import com.ktinsta.server.service.PostService
import com.ktinsta.server.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import kotlin.coroutines.coroutineContext

@RestController
@RequestMapping("/api/post/")
class PostController(val postService: PostService, val userService: UserService){
    @PostMapping("/create")
    fun createPost(@Valid @RequestBody postDetails: PostNet, response: HttpServletResponse): ResponseEntity<Any> {
        val author = userService.findById(postDetails.userId)

        val post = Post(author = author, text = postDetails.text)
        val image = Image(data = postDetails.data, post = post)

        postService.create(post, image)

        return ResponseEntity.ok(ResponseConstants.SUCCESS.value)
    }
}

data class PostNet(var userId: Long, var text: String, var data: ByteArray)
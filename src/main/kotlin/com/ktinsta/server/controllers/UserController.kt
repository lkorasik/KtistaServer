package com.ktinsta.server.controllers

import com.ktinsta.server.components.UserAssembler
import com.ktinsta.server.constants.ResponseConstants
import com.ktinsta.server.helpers.objects.LoginVO
import com.ktinsta.server.helpers.objects.UserVO
import com.ktinsta.server.model.Post
import com.ktinsta.server.model.User
import com.ktinsta.server.repository.UserRepository
import com.ktinsta.server.service.UserServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import kotlin.collections.HashMap

@RestController
@RequestMapping("/api/post/")
class PostController{
    @PostMapping("/create")
    fun createPost(@Valid @RequestBody post: PostNet, response: HttpServletResponse): ResponseEntity<Any>{
        println("Create post")

        return ResponseEntity.ok(ResponseConstants.SUCCESS.value)
    }
}

data class PostNet(var userId: Long, var text: String, var data: ByteArray)

@RestController
@RequestMapping("/api/user")
class UserController(val userRepository: UserRepository, val userService: UserServiceImpl, val userAssembler: UserAssembler) {
    @PostMapping("/registration")
    fun createNewUser(@Valid @RequestBody userDetails: User, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.attemptRegistration(userDetails)

        val issuer = user.id.toString()
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() * 60 * 24 * 1000)) // 1 day expiration
            .signWith(SignatureAlgorithm.HS512, "secretKey")
            .compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    // FIXME: внедряя это говно мы должны хранить cookies, и проверять в других методах контроллера
    // FIXME: нужно будет переписать, чтобы в заголовок сувалось, но пока что так
    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginDetails: LoginVO,
                  response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.attemptLogin(loginDetails)

        val issuer = user.id.toString()
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() * 60 * 24 * 1000)) // 1 day expiration
            .signWith(SignatureAlgorithm.HS512, "secretKey")
            .compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity.ok(ResponseConstants.SUCCESS.value)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<UserVO> {
        val user = userService.retrieveUserData(userId)
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }
}
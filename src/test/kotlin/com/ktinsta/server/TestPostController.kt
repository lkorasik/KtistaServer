package com.ktinsta.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ktinsta.server.controllers.dto.CreatePostVO
import com.ktinsta.server.controllers.dto.LoginVO
import com.ktinsta.server.controllers.dto.RegistrationVO
import com.ktinsta.server.storage.repository.PostRepository
import com.ktinsta.server.storage.repository.FullUserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class `Test PostController` {
    @Autowired
    private lateinit var fullUserRepository: FullUserRepository
    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @AfterEach
    fun `Clear database`(){
        postRepository.deleteAll()
        fullUserRepository.deleteAll()
    }

    private lateinit var jwt: String

    @BeforeEach
    fun `Register new user and login`(){
        val registration = RegistrationVO("Test", "123456", "test@test.test")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk)

        val login = LoginVO("Test", "123456")

        val post2 = MockMvcRequestBuilders.post("/api/auth/login")
            .content(jacksonObjectMapper().writeValueAsString(login))
            .contentType(MediaType.APPLICATION_JSON)

        val response = mockMvc.perform(post2).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        val authHeaderContent = response.response.getHeaderValue("Authorization").toString()

        jwt = authHeaderContent.split(" ")[1]
    }

    @Test
    fun `Create new post`(){
        val newPost = CreatePostVO("Test", ByteArray(1024))

        val post = MockMvcRequestBuilders.post("/api/post/create")
            .header("Authorization", "Bearer $jwt")
            .content(jacksonObjectMapper().writeValueAsString(newPost))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Create new post without text`(){
        val newPost = CreatePostVO("", ByteArray(1024))

        val post = MockMvcRequestBuilders.post("/api/post/create")
            .header("Authorization", "Bearer $jwt")
            .content(jacksonObjectMapper().writeValueAsString(newPost))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk)
    }
}
package com.ktinsta.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ktinsta.server.controllers.dto.*
import com.ktinsta.server.controllers.dto.ReturnPostDTO
import com.ktinsta.server.storage.repository.PostRepository
import com.ktinsta.server.storage.repository.FullUserRepository
import org.assertj.core.api.Assertions.assertThat
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
class `Test UserController` {
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
        val registration = RegistrationDTO("Test", "123456", "test@test.test")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk)

        val login = LoginDTO("Test", "123456")

        val post2 = MockMvcRequestBuilders.post("/api/auth/login")
            .content(jacksonObjectMapper().writeValueAsString(login))
            .contentType(MediaType.APPLICATION_JSON)

        val response = mockMvc.perform(post2).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        val authHeaderContent = response.response.getHeaderValue("Authorization").toString()

        jwt = authHeaderContent.split(" ")[1]
    }

    @Test
    fun `Get profile`(){
        val post = MockMvcRequestBuilders.get("/api/user/profile")
            .header("Authorization", "Bearer $jwt")

        val result = mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        val user = jacksonObjectMapper().readValue(result.response.contentAsByteArray, FullUserDTO::class.java)

        assertThat(user.username).isEqualTo("Test")
        assertThat(user.followersCount).isEqualTo(0)
        assertThat(user.followingsCount).isEqualTo(0)
        assertThat(user.avatar).isNull()
    }

    @Test
    fun `Set and get settings`(){
        val settings = UserSettingsDTO(null, "test@test.test", "Test")

        val settingsReq = MockMvcRequestBuilders.post("/api/user/settings")
            .header("Authorization", "Bearer $jwt")
            .content(jacksonObjectMapper().writeValueAsString(settings))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(settingsReq).andExpect(MockMvcResultMatchers.status().isOk)

        val post = MockMvcRequestBuilders.get("/api/user/settings")
            .header("Authorization", "Bearer $jwt")

        val result = mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        val setting = jacksonObjectMapper().readValue(result.response.contentAsByteArray, UserSettingsDTO::class.java)

        assertThat(setting.email).isEqualTo("test@test.test")
        assertThat(setting.username).isEqualTo("Test")
    }

    @Test
    fun `Get all user's posts`(){
        val newPost = CreatePostDTO("Test text", ByteArray(1024))

        val postR = MockMvcRequestBuilders.post("/api/post/create")
            .header("Authorization", "Bearer $jwt")
            .content(jacksonObjectMapper().writeValueAsString(newPost))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(postR).andExpect(MockMvcResultMatchers.status().isOk)
        mockMvc.perform(postR).andExpect(MockMvcResultMatchers.status().isOk)

        val post = MockMvcRequestBuilders.get("/api/user/all-my-posts")
            .header("Authorization", "Bearer $jwt")

        val result = mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        val posts = jacksonObjectMapper().readValue(result.response.contentAsByteArray, Array<ReturnPostDTO>::class.java)

        assertThat(posts.map { it.author.username }).isEqualTo(arrayListOf("Test", "Test"))
        assertThat(posts.map { it.text }).isEqualTo(arrayListOf("Test text", "Test text"))
    }
}
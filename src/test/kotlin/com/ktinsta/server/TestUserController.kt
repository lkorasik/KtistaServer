package com.ktinsta.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.ktinsta.server.helpers.objects.*
import com.ktinsta.server.repository.PostRepository
import com.ktinsta.server.repository.UserRepository
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
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @AfterEach
    fun `Clear database`(){
        userRepository.deleteAll()
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
    fun `Get profile`(){
        val post = MockMvcRequestBuilders.get("/api/user/profile")
            .header("Authorization", "Bearer $jwt")

        val result = mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        val user = jacksonObjectMapper().readValue(result.response.contentAsByteArray, UserVO::class.java)

        assertThat(user.username).isEqualTo("Test")
        assertThat(user.followers).isEqualTo(0)
        assertThat(user.followings).isEqualTo(0)
        assertThat(user.image).isNull()
    }
}
package com.ktinsta.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ktinsta.server.controllers.dto.RegistrationVO
import com.ktinsta.server.storage.repository.UserRepository
import org.junit.jupiter.api.AfterEach
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
class `Test AuthController` {

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @AfterEach
    fun `Clear database`(){
        repository.deleteAll()
    }

    @Test
    fun `Register new user`(){
        val registration = RegistrationVO("Test", "123456", "test@test.test")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Register new user with empty password`(){
        val registration = RegistrationVO("Test", "", "test@test.test")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `Register new user with empty email`(){
        val registration = RegistrationVO("Test", "123456", "")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `Register new user with empty nickname`(){
        val registration = RegistrationVO("", "123456", "test@test.test")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `Register two identical new users`(){
        val username = "Test"
        val registration = RegistrationVO(username, "123456", "test@test.test")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_001",
    "errorMessage": "User with username: $username is already exists."
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isOk)

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))

    }
}
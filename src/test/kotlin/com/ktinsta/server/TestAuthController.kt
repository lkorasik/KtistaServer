package com.ktinsta.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ktinsta.server.controllers.dto.RegistrationDTO
import com.ktinsta.server.storage.repository.FullUserRepository
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
    private lateinit var repository: FullUserRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val username = "Einstein"
    private val password = "123Password!"
    private val email = "Albert.Einstein@gmail.com"

    @AfterEach
    fun `Clear database`(){
        repository.deleteAll()
    }

    @Test
    fun `Register new user`(){
        val registration = RegistrationDTO("Test", "123456", "test@test.test")

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Register new user with empty password`(){
        val registration = RegistrationDTO("Test", "", "test@test.test")

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_008",
    "errorMessage": "Password too short"
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))
    }

    @Test
    fun `Register new user with empty email`(){
        val registration = RegistrationDTO("Test", "123456", "")

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
    }

    @Test
    fun `Register new user with empty nickname`(){
        val registration = RegistrationVO("", password, email)

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
    }

    @Test
    fun `Register new user with too short username`(){
        val registration = RegistrationVO("Te", password, email)

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_005",
    "errorMessage": "Username too short"
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))
    }

    @Test
    fun `Register new user with too long username`(){
        val registration = RegistrationVO("TestTestTestTestTestTestTestTestTest", password, email)

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_006",
    "errorMessage": "Username too long"
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))
    }

    @Test
    fun `Register new user with invalid username`(){
        val registration = RegistrationVO("Test!", password, email)

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_007",
    "errorMessage": "Username contains unavailable symbols"
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))
    }

    @Test
    fun `Register new user with too short password`(){
        val registration = RegistrationVO(username, "Pa", email)

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_008",
    "errorMessage": "Password too short"
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))
    }

    @Test
    fun `Register new user with too long password`(){
        val registration = RegistrationVO(username, "123456789012345678901234567890123456789012345678901234567890", email)

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_009",
    "errorMessage": "Password too long"
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))
    }

    @Test
    fun `Register new user with invalid password`(){
        val registration = RegistrationVO(username, "$password ", email)

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_010",
    "errorMessage": "Password contains unavailable symbols"
}
        """.trimIndent()

        mockMvc
            .perform(post)
            .andExpect(MockMvcResultMatchers
                .status()
                .isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers
                .content()
                .json(expected))
    }

    @Test
    fun `Register two identical new users`(){
        val username = "Test"
        val registration = RegistrationDTO(username, "123456", "test@test.test")

        val post = MockMvcRequestBuilders
            .post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        val expected = """
{
    "errorCode": "USR_001",
    "errorMessage": "User with username $username is already exists"
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

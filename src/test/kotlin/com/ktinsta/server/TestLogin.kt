package com.ktinsta.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ktinsta.server.helpers.objects.LoginVO
import com.ktinsta.server.helpers.objects.RegistrationVO
import com.ktinsta.server.repository.UserRepository
import org.assertj.core.api.Assertions
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
class `Test login` {
    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @AfterEach
    fun `Clear database`(){
        repository.deleteAll()
    }

    @BeforeEach
    fun `Register user`(){
        val registration = RegistrationVO("Test", "123456", "test@test.test")

        val post = MockMvcRequestBuilders.post("/api/auth/registration")
            .content(jacksonObjectMapper().writeValueAsString(registration))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(post).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Login user`(){
        val login = LoginVO("Test", "123456")

        val post2 = MockMvcRequestBuilders.post("/api/auth/login")
            .content(jacksonObjectMapper().writeValueAsString(login))
            .contentType(MediaType.APPLICATION_JSON)

        val response = mockMvc.perform(post2).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        val authHeaderContent = response.response.getHeaderValue("Authorization").toString()

        assertThat(authHeaderContent).matches("Bearer [A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*")
    }

    @Test
    fun `Multiple login user`(){
        val jwts = arrayOfNulls<String>(3)

        for(i in 0..2) {
            val login = LoginVO("Test", "123456")

            val post2 = MockMvcRequestBuilders.post("/api/auth/login")
                .content(jacksonObjectMapper().writeValueAsString(login))
                .contentType(MediaType.APPLICATION_JSON)

            val response = mockMvc.perform(post2).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
            val authHeaderContent = response.response.getHeaderValue("Authorization").toString()

            assertThat(authHeaderContent).matches("Bearer [A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*")

            jwts[i] = authHeaderContent
        }

        assertThat(jwts).allMatch { it.equals(jwts[0]) }
    }
}
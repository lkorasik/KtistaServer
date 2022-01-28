package com.ktinsta.server

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class `Test unauthorized access` {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    fun `Unauthorized access to root`() {
        mockMvc!!
            .perform(get("/"))
            .andDo(print())
            .andExpect(status().isForbidden)
            .andExpect(content().string(""))
    }

    @Test
    fun `Unauthorized access to UserController getProfile`() {
        mockMvc!!
            .perform(get("/api/user/profile/1"))
            .andDo(print())
            .andExpect(status().isForbidden)
            .andExpect(content().string(""))
    }

    @Test
    fun `Unauthorized access to UserController getSettings`() {
        mockMvc!!
            .perform(get("/api/user/settings/1"))
            .andDo(print())
            .andExpect(status().isForbidden)
            .andExpect(content().string(""))
    }

    @Test
    fun `Unauthorized access to UserController setSettings`() {
        mockMvc!!
            .perform(post("/api/user/settings/1"))
            .andDo(print())
            .andExpect(status().isForbidden)
            .andExpect(content().string(""))
    }

    @Test
    fun `Unauthorized access to PostController createPost`() {
        mockMvc!!
            .perform(post("/api/post/create"))
            .andDo(print())
            .andExpect(status().isForbidden)
            .andExpect(content().string(""))
    }
}
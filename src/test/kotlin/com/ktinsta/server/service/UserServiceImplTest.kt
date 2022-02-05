package com.ktinsta.server.service

import com.ktinsta.server.controllers.dto.RegistrationVO
import com.ktinsta.server.storage.repository.BriefUserRepository
import com.ktinsta.server.storage.repository.FullUserRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.util.Assert

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private lateinit var userServiceImpl: UserServiceImpl

    @MockBean
    private lateinit var fullUserRepository: FullUserRepository

    @MockBean
    private lateinit var briefUserRepository: BriefUserRepository

    @Test
    fun `Valid user's data`() {
        val reg = RegistrationVO(
            username = "SkyRee",
            password = "05022022SR",
            email = "skyree@gmail.com"
        )

        val result = userServiceImpl.isValid(reg)

        Assert.isTrue(result, "Should be true")
    }
}
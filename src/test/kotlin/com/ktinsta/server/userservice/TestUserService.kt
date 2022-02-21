package com.ktinsta.server.userservice

import com.ktinsta.server.controllers.dto.RegistrationDTO
import com.ktinsta.server.exceptions.UsernameUnavailableException
import com.ktinsta.server.service.UserServiceImpl
import com.ktinsta.server.storage.repository.BriefUserRepository
import com.ktinsta.server.storage.repository.FullUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class `Test User Service registration` {
    @Autowired
    private lateinit var userService: UserServiceImpl

    @MockBean
    private lateinit var fullUserRepository: FullUserRepository
    @MockBean
    private lateinit var briefUserRepository: BriefUserRepository

    private val username = "EinsteinTestovich"
    private val password = "myTheoryOfGravity"
    private val email = "Einstein@science.com"

    @Test
    fun `Validate registration data`() {
        val registrationDTO = RegistrationDTO(username, password, email)

        val isValid = userService.isValid(registrationDTO)

        assertThat(isValid).isTrue
    }

    @Test
    fun `Empty username`() {
        val registrationDTO = RegistrationDTO("", password, email)

        val isValid = userService.isValid(registrationDTO)

        assertThat(isValid).isFalse
    }

    @Test
    fun `Empty password`() {
        val registrationDTO = RegistrationDTO(username, "", email)

        val isValid = userService.isValid(registrationDTO)

        assertThat(isValid).isFalse
    }

    @Test
    fun `Empty email`() {
        val registrationDTO = RegistrationDTO(username, password, "")

        val isValid = userService.isValid(registrationDTO)

        assertThat(isValid).isFalse
    }

    @Test
    fun `Register new user`() {
        val registrationDTO = RegistrationDTO(username, password, email)

        val user = userService.attemptRegistration(registrationDTO)

        Mockito.verify(fullUserRepository, Mockito.times(1)).save(user)
    }

    @Test
    fun `Double register new user`() {
        val registrationDTO = RegistrationDTO(username, password, email)

        val sp = Mockito.spy(userService)

        Mockito.doReturn(false).`when`(sp).usernameExists(username)

        userService.attemptRegistration(registrationDTO)

        Mockito.doReturn(true).`when`(sp).usernameExists(username)

        Mockito.`when`(userService.attemptRegistration(registrationDTO)).thenThrow(UsernameUnavailableException::class.java)
    }
}

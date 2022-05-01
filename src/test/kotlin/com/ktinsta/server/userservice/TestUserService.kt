package com.ktinsta.server.userservice

import com.ktinsta.server.controllers.dto.RegistrationDTO
import com.ktinsta.server.exceptions.EmailUnavailableException
import com.ktinsta.server.exceptions.UsernameUnavailableException
import com.ktinsta.utils.failed
import com.ktinsta.utils.passed
import com.ktinsta.server.service.UserServiceImpl
import com.ktinsta.server.storage.model.FullUser
import com.ktinsta.server.storage.repository.BriefUserRepository
import com.ktinsta.server.storage.repository.FullUserRepository
import org.junit.jupiter.api.BeforeEach
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

    private val username = "Einstein"
    private val password = "myTheoryOfGravity"
    private val email = "Einstein@science.com"

    private lateinit var registrationDTO: RegistrationDTO
    private lateinit var expectedUser: FullUser

    @BeforeEach
    fun prepareForEachTest() {
        registrationDTO = RegistrationDTO(username, password, email)
        expectedUser = FullUser().also {
            it.username = username
            it.email = email
            it.password = password
        }
    }

    @Test
    fun `Register new user`() {
        Mockito.`when`(fullUserRepository.findByUsername(username)).thenReturn(null)
        Mockito.`when`(fullUserRepository.findByEmail(email)).thenReturn(null)

        userService.attemptRegistration(registrationDTO)

        Mockito.verify(fullUserRepository, Mockito.times(1)).findByUsername(username)
        Mockito.verify(fullUserRepository, Mockito.times(1)).findByEmail(email)
        Mockito.verify(fullUserRepository, Mockito.times(1)).save(Mockito.any())
    }

    @Test
    fun `Register new user when username already exists`() {
        Mockito.`when`(fullUserRepository.findByUsername(username)).thenReturn(expectedUser)
        Mockito.`when`(fullUserRepository.findByEmail(email)).thenReturn(null)

        try {
            userService.attemptRegistration(registrationDTO)
            failed()
        } catch (exception: UsernameUnavailableException) {
            passed()
        } catch (exception: Exception) {
            failed()
        }

        Mockito.verify(fullUserRepository, Mockito.times(1)).findByUsername(username)
        Mockito.verify(fullUserRepository, Mockito.times(0)).findByEmail(email)
        Mockito.verify(fullUserRepository, Mockito.times(0)).save(Mockito.any())
    }

    @Test
    fun `Register new user when email already exists`() {
        Mockito.`when`(fullUserRepository.findByUsername(username)).thenReturn(null)
        Mockito.`when`(fullUserRepository.findByEmail(email)).thenReturn(expectedUser)

        try {
            userService.attemptRegistration(registrationDTO)
            failed()
        } catch (exception: EmailUnavailableException) {
            passed()
        } catch (exception: Exception) {
            failed()
        }

        Mockito.verify(fullUserRepository, Mockito.times(1)).findByUsername(username)
        Mockito.verify(fullUserRepository, Mockito.times(1)).findByEmail(email)
        Mockito.verify(fullUserRepository, Mockito.times(0)).save(Mockito.any())
    }
}

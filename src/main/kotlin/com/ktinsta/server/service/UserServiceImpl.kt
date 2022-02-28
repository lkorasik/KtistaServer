package com.ktinsta.server.service

import com.ktinsta.server.controllers.dto.LoginDTO
import com.ktinsta.server.controllers.dto.RegistrationDTO
import com.ktinsta.server.controllers.dto.UserSettingsDTO
import com.ktinsta.server.exceptions.*
import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.Image
import com.ktinsta.server.storage.model.FullUser
import com.ktinsta.server.storage.repository.BriefUserRepository
import com.ktinsta.server.storage.repository.FullUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val fullUserRepository: FullUserRepository,
    private val briefUserRepository: BriefUserRepository) : UserService {

    @Throws(UsernameUnavailableException::class, EmailUnavailableException::class)
    override fun attemptRegistration(userDetails: RegistrationDTO) {
        if(usernameExists(userDetails.username))
            throw UsernameUnavailableException("User with username ${userDetails.username} is already exists")
        if(emailExists(userDetails.email))
            throw EmailUnavailableException("User with email ${userDetails.email} is already exists")

        val fullUser = FullUser().apply {
            username = userDetails.username
            email = userDetails.email
            password = userDetails.password
        }
        fullUserRepository.save(fullUser)
    }

    // TODO: refactoring
    @Throws(InvalidUsernameException::class)
    override fun attemptLogin(loginDetails: LoginDTO): FullUser {
        if (usernameExists(loginDetails.username)) {
            val user = fullUserRepository.findByUsername(loginDetails.username)
            if (user != null) {
                return validatePassword(loginDetails, user)
            }
        }
        throw InvalidUsernameException("No user with username: ${loginDetails.username} has been found")
    }

    override fun listUsers(currentUser: FullUser): List<FullUser> {
        return fullUserRepository.findAll()
            .mapTo(ArrayList()) { it }
            .filter { it != currentUser }
    }

    @Throws(InvalidUserIdException::class)
    override fun retrieveFullUserData(id: Long): FullUser {
        val userOptional = fullUserRepository.findById(id)
        if (userOptional.isPresent) {
            val user = userOptional.get()
            obscurePassword(user)
            return user
        }
        throw InvalidUserIdException("User with id: $id doesn't exist.")
    }

    override fun retrieveFullUserData(username: String): FullUser? {
        val user = fullUserRepository.findByUsername(username)
        obscurePassword(user)
        return user
    }

    @Throws(Exception::class)
    override fun usernameExists(username: String): Boolean {
        return fullUserRepository.findByUsername(username) != null
    }

    @Throws(Exception::class)
    fun emailExists(email: String): Boolean {
        return fullUserRepository.findByEmail(email) != null
    }

    override fun retrieveBriefUserData(id: Long): BriefUser? {
        val userOptional = briefUserRepository.findById(id)
        if (userOptional.isPresent) {
            return userOptional.get()
        }
        throw InvalidUserIdException("User with id: $id doesn't exist.")
    }

    override fun retrieveBriefUserData(username: String): BriefUser? {
        val user = briefUserRepository.findByUsername(username)
        user?.let {
            return user
        }
        throw InvalidUserIdException("User with username: $username doesn't exist.")
    }

    @Throws(InvalidPasswordException::class)
    fun validatePassword(loginDetails: LoginDTO, userDetails: FullUser) : FullUser {
        val isValid = BCryptPasswordEncoder().matches(loginDetails.password, userDetails.password)
        if (isValid) {
            obscurePassword(userDetails)
            return userDetails
        }
        throw InvalidPasswordException("Password for user: ${userDetails.username} is incorrect.")
    }

    fun getSettings(id: Long): UserSettingsDTO {
        fullUserRepository.apply {
            val currentSettings = findById(id).get()

            return UserSettingsDTO(
                avatar = currentSettings.avatar?.data,
                email = currentSettings.email,
                username = currentSettings.username
            )
        }
    }

    fun setSettings(id: Long, userSettings: UserSettingsDTO){
        fullUserRepository.apply {
            val currentSetSettings = findById(id).get()

            currentSetSettings.avatar = userSettings.avatar?.let { Image(data = it) }
            currentSetSettings.email = userSettings.email
            currentSetSettings.username = userSettings.username

            fullUserRepository.save(currentSetSettings)
        }
    }

    fun obscurePassword(fullUser: FullUser?) {
        fullUser?.password = "XXX XXX XXX"
    }
}

package com.ktinsta.server.service

import com.ktinsta.server.controllers.dto.LoginVO
import com.ktinsta.server.controllers.dto.RegistrationVO
import com.ktinsta.server.controllers.dto.UserSettingsVO
import com.ktinsta.server.exceptions.InvalidPasswordException
import com.ktinsta.server.exceptions.InvalidUserIdException
import com.ktinsta.server.exceptions.InvalidUsernameException
import com.ktinsta.server.exceptions.UsernameUnavailableException
import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.Image
import com.ktinsta.server.storage.model.FullUser
import com.ktinsta.server.storage.repository.BriefUserRepository
import com.ktinsta.server.storage.repository.FullUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val fullUserRepository: FullUserRepository, val briefUserRepository: BriefUserRepository) : UserService {

    fun isValid(registrationVO: RegistrationVO): Boolean {
        return registrationVO.run {
            email.isNotBlank() && password.isNotBlank() && username.isNotBlank()
        }
    }

    @Throws(UsernameUnavailableException::class)
    override fun attemptRegistration(userDetails: RegistrationVO): FullUser {
        if (!usernameExists(userDetails.username)) {
            val fullUser = FullUser()
            fullUser.username = userDetails.username
            fullUser.email = userDetails.email
            fullUser.password = userDetails.password
            fullUserRepository.save(fullUser)
            obscurePassword(fullUser)

            return fullUser
        }
        throw UsernameUnavailableException("User with " +
                "username: ${userDetails.username} is already exists.")
    }

    // TODO: refactoring
    @Throws(InvalidUsernameException::class)
    override fun attemptLogin(loginDetails: LoginVO): FullUser {
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

    override fun retrieveBriefUserData(id: Long): BriefUser? {
        val userOptional = briefUserRepository.findById(id)
        if (userOptional.isPresent) {
            return userOptional.get()
        }
        throw InvalidUserIdException("User with id: $id doesn't exist.")
    }

    @Throws(InvalidPasswordException::class)
    fun validatePassword(loginDetails: LoginVO, userDetails: FullUser) : FullUser {
        val isValid = BCryptPasswordEncoder().matches(loginDetails.password, userDetails.password)
        if (isValid) {
            obscurePassword(userDetails)
            return userDetails
        }
        throw InvalidPasswordException("Password for user: ${userDetails.username} is incorrect.")
    }

    fun getSettings(id: Long): UserSettingsVO {
        fullUserRepository.apply {
            val currentSettings = findById(id).get()

            return UserSettingsVO(
                avatar = currentSettings.avatar?.data,
                email = currentSettings.email,
                nickname = currentSettings.username
            )
        }
    }

    fun setSettings(id: Long, userSettings: UserSettingsVO){
        fullUserRepository.apply {
            val currentSetSettings = findById(id).get()

            val img = userSettings.avatar?.let { Image(data = it) }

            currentSetSettings.avatar = userSettings.avatar?.let { Image(data = it) }
            currentSetSettings.email = userSettings.email
            currentSetSettings.username = userSettings.nickname

            fullUserRepository.save(currentSetSettings)
        }
    }

    fun obscurePassword(fullUser: FullUser?) {
        fullUser?.password = "XXX XXX XXX"
    }
}
package com.ktinsta.server.service

import com.ktinsta.server.exceptions.*
import com.ktinsta.server.helpers.objects.LoginVO
import com.ktinsta.server.model.User
import com.ktinsta.server.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(val repository: UserRepository) : UserService {

    @Throws(UsernameUnavailableException::class)
    override fun attemptRegistration(userDetails: User): User {
        if (!usernameExists(userDetails.username)) {
            val user = User()
            user.username = userDetails.username
            user.email = userDetails.email
            user.password = userDetails.password
            repository.save(user)
            obscurePassword(user)

            return user
        }
        throw UsernameUnavailableException("User with " +
                "username: ${userDetails.username} is already exists.")
    }

    // TODO: refactoring
    @Throws(InvalidUsernameException::class)
    override fun attemptLogin(loginDetails: LoginVO): User {
        if (usernameExists(loginDetails.username)) {
            val user = repository.findByUsername(loginDetails.username)
            if (user != null) {
                return validatePassword(loginDetails, user)
            }
        }
        throw InvalidUsernameException("No user with username: ${loginDetails.username} has been found")
    }

    override fun listUsers(currentUser: User): List<User> {
        return repository.findAll()
            .mapTo(ArrayList(), { it })
            .filter { it != currentUser }
    }

    @Throws(InvalidUserIdException::class)
    override fun retrieveUserData(id: Long): User {
        val userOptional = repository.findById(id)
        if (userOptional.isPresent) {
            val user = userOptional.get()
            obscurePassword(user)
            return user
        }
        throw InvalidUserIdException("User with id: $id doesn't exist.")
    }

    override fun retrieveUserData(username: String): User? {
        val user = repository.findByUsername(username)
        obscurePassword(user)
        return user
    }

    @Throws(Exception::class)
    override fun usernameExists(username: String): Boolean {
        return repository.findByUsername(username) != null
    }

    override fun findById(id: Long): User = repository.findById(id).get()

    @Throws(UserStatusEmptyException::class)
    fun updateUserStatus(currentUser: User, userDetails: User): User {
        if (!currentUser.status.isEmpty()) {
            currentUser.status = userDetails.status
            repository.save(currentUser)
            return currentUser
        }
        throw UserStatusEmptyException("A user's status can't be empty.")
    }

    @Throws(InvalidPasswordException::class)
    fun validatePassword(loginDetails: LoginVO, userDetails: User) : User {
        val isValid = BCryptPasswordEncoder()
            .matches(loginDetails.password, userDetails.password)
        if (isValid)
            return userDetails

        throw InvalidPasswordException("Password for user: ${userDetails.username} is incorrect.")
    }

    fun obscurePassword(user: User?) {
        user?.password = "XXX XXX XXX"
    }
}
package com.ktinsta.server.service

import com.ktinsta.server.helpers.objects.LoginVO
import com.ktinsta.server.helpers.objects.RegistrationVO
import com.ktinsta.server.model.User
import java.util.*

interface UserService {
    fun attemptRegistration(userDetails: RegistrationVO): User
    fun attemptLogin(loginDetails: LoginVO): User
    fun listUsers(currentUser: User): List<User>
    fun retrieveUserData(username: String): User?
    fun retrieveUserData(id: Long): User?
    fun usernameExists(username: String): Boolean
}
package com.ktinsta.server.service

import com.ktinsta.server.controllers.dto.LoginVO
import com.ktinsta.server.controllers.dto.RegistrationVO
import com.ktinsta.server.storage.model.User

interface UserService {
    fun attemptRegistration(userDetails: RegistrationVO): User
    fun attemptLogin(loginDetails: LoginVO): User
    fun listUsers(currentUser: User): List<User>
    fun retrieveUserData(username: String): User?
    fun retrieveUserData(id: Long): User?
    fun usernameExists(username: String): Boolean
}
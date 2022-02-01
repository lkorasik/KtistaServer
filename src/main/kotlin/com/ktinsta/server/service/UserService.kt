package com.ktinsta.server.service

import com.ktinsta.server.controllers.dto.LoginVO
import com.ktinsta.server.controllers.dto.RegistrationVO
import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.FullUser

interface UserService {
    fun attemptRegistration(userDetails: RegistrationVO): FullUser
    fun attemptLogin(loginDetails: LoginVO): FullUser
    fun listUsers(currentUser: FullUser): List<FullUser>
    fun retrieveFullUserData(username: String): FullUser?
    fun retrieveFullUserData(id: Long): FullUser?
    fun usernameExists(username: String): Boolean

    fun retrieveBriefUserData(id: Long): BriefUser?
}
package com.ktinsta.server.service

import com.ktinsta.server.controllers.dto.LoginDTO
import com.ktinsta.server.controllers.dto.RegistrationDTO
import com.ktinsta.server.storage.model.BriefUser
import com.ktinsta.server.storage.model.FullUser

interface UserService {
    fun attemptRegistration(userDetails: RegistrationDTO)
    fun attemptLogin(loginDetails: LoginDTO): FullUser
    fun listUsers(currentUser: FullUser): List<FullUser>
    fun retrieveFullUserData(username: String): FullUser?
    fun retrieveFullUserData(id: Long): FullUser?
    fun usernameExists(username: String): Boolean

    fun retrieveBriefUserData(id: Long): BriefUser?
    fun retrieveBriefUserData(username: String): BriefUser?
}

package com.ktinsta.server.exceptions

import java.lang.RuntimeException

class UsernameUnavailableException(override val message: String)
    :RuntimeException()

class InvalidUserIdException(override val message: String)
    :RuntimeException()

class InvalidUsernameException(override val message: String)
    :RuntimeException()

class InvalidPasswordException(override val message: String)
    :RuntimeException()

class UserDeactivatedException(override val message: String)
    :RuntimeException()

class UserStatusEmptyException(override val message: String)
    :RuntimeException()
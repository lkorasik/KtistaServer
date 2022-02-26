package com.ktinsta.server.exceptions

import kotlin.RuntimeException

class UsernameUnavailableException(override val message: String)
    :RuntimeException()

class InvalidUserIdException(override val message: String)
    :RuntimeException()

class InvalidUsernameException(override val message: String)
    :RuntimeException()

class InvalidPasswordException(override val message: String)
    :RuntimeException()

class UserStatusEmptyException(override val message: String)
    :RuntimeException()

class TooShortUsernameException(override val message: String): RuntimeException()

class TooLongUsernameException(override val message: String): RuntimeException()

class InvalidSymbolsUsernameException(override val message: String): RuntimeException()

class NotUniqueUsernameException(override val message: String): RuntimeException()

class TooShortPasswordException(override val message: String): RuntimeException()

class TooLongPasswordException(override val message: String): RuntimeException()

class InvalidSymbolsPasswordException(override val message: String): RuntimeException()

class InvalidEmailException(override val message: String): RuntimeException()

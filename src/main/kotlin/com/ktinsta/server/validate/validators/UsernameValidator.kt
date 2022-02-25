package com.ktinsta.server.validate.validators

import com.ktinsta.server.exceptions.InvalidSymbolsUsernameException
import com.ktinsta.server.exceptions.NotUniqueUsernameException
import com.ktinsta.server.exceptions.TooLongUsernameException
import com.ktinsta.server.exceptions.TooShortUsernameException
import com.ktinsta.server.service.UserService
import com.ktinsta.server.validate.annotations.Username
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UsernameValidator(private val userService: UserService) : ConstraintValidator<Username, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        if (value.length < 3)
            throw TooShortUsernameException("Username too short")
        if (value.length > 25)
            throw TooLongUsernameException("Username too long")

        if(!isValidSymbols(value))
            throw InvalidSymbolsUsernameException("Username contains unavailable symbols")

        if(!isUniqueUsername(value))
            throw NotUniqueUsernameException("User with username $value is already exists")

        return true
    }

    private fun isValidSymbols(username: String) = Regex("[a-zA-Z0-9]+").matches(username)

    private fun isUniqueUsername(username: String) = !userService.usernameExists(username)
}

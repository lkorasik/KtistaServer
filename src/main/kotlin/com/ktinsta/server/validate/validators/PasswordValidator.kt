package com.ktinsta.server.validate.validators

import com.ktinsta.server.exceptions.*
import com.ktinsta.server.service.UserService
import com.ktinsta.server.validate.annotations.ValidatePassword
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordValidator(private val userService: UserService) : ConstraintValidator<ValidatePassword, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        if (value.length < 3)
            throw TooShortPasswordException("Password too short")
        if (value.length > 25)
            throw TooLongPasswordException("Password too long")

        if(!isValidSymbols(value))
            throw InvalidSymbolsPasswordException("Password contains unavailable symbols")

        return true
    }

    private fun isValidSymbols(username: String) = Regex("[a-zA-Z0-9~`!@\"\'#$%^&*()_\\+\\-=*/\\\\|><.,?\\[\\]{}]+").matches(username)
}

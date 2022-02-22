package com.ktinsta.server.validate.validators

import com.ktinsta.server.exceptions.InvalidEmailException
import com.ktinsta.server.validate.annotations.Email
import org.apache.commons.validator.routines.DomainValidator
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

private typealias BaseEmailValidator = org.apache.commons.validator.routines.EmailValidator

class EmailValidator: ConstraintValidator<Email, String> {
    private val validator = BaseEmailValidator(false, false, DomainValidator.getInstance())

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        if(!validator.isValid(value))
            throw InvalidEmailException("Invalid email")

        return true
    }
}
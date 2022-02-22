package com.ktinsta.server.validate.annotations

import com.ktinsta.server.validate.validators.UsernameValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UsernameValidator::class])
annotation class Username (
    val message: String = "Invalid username",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
package com.ktinsta.server.components

import com.ktinsta.server.constants.ErrorResponse
import com.ktinsta.server.constants.ResponseConstants
import com.ktinsta.server.exceptions.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Use to process user's controller exceptions.
 */

@ControllerAdvice
class UserControllerAdvice {

    @ExceptionHandler(UsernameUnavailableException::class)
    fun usernameUnavailable(usernameUnavailableException: UsernameUnavailableException)
    : ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.USERNAME_UNAVAILABLE.value,
            usernameUnavailableException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidUsernameException::class)
    fun invalidUsernameException(invalidUsernameException: InvalidUsernameException)
    : ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.INVALID_USERNAME.value,
            invalidUsernameException.message
        )

        return ResponseEntity.badRequest().body(res)
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordException(invalidPasswordException: InvalidPasswordException)
            : ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.INVALID_PASSWORD.value,
            invalidPasswordException.message
        )

        return ResponseEntity.badRequest().body(res)
    }

    @ExceptionHandler(InvalidUserIdException::class)
    fun invalidId(invalidUserIdException: InvalidUserIdException)
            : ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.INVALID_USER_ID.value,
            invalidUserIdException.message
        )

        return ResponseEntity.badRequest().body(res)
    }

    @ExceptionHandler(UserStatusEmptyException::class)
    fun statusEmpty(statusEmptyException: UserStatusEmptyException)
            : ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.EMPTY_STATUS.value,
            statusEmptyException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }
}
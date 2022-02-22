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

    @ExceptionHandler(TooShortUsernameException::class)
    fun shortUsername(tooShortUsernameException: TooShortUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.TOO_SHORT_USERNAME.value,
            tooShortUsernameException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(TooLongUsernameException::class)
    fun longUsername(tooLongUsernameException: TooLongUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.TOO_LONG_USERNAME.value,
            tooLongUsernameException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidSymbolsUsernameException::class)
    fun invalidSymbolsUsername(invalidSymbolsUsernameException: InvalidSymbolsUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.INVALID_USERNAME_SYMBOLS.value,
            invalidSymbolsUsernameException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(NotUniqueUsernameException::class)
    fun invalidSymbolsUsername(notUniqueUsernameException: NotUniqueUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.USERNAME_UNAVAILABLE.value,
            notUniqueUsernameException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(TooShortPasswordException::class)
    fun shortPassword(tooShortPasswordException: TooShortPasswordException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.TOO_SHORT_PASSWORD.value,
            tooShortPasswordException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(TooLongPasswordException::class)
    fun longPassword(tooLongPasswordException: TooLongPasswordException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.TOO_LONG_PASSWORD.value,
            tooLongPasswordException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidSymbolsPasswordException::class)
    fun invalidSymbolsPassword(invalidSymbolsPasswordException: InvalidSymbolsPasswordException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.INVALID_PASSWORD_SYMBOLS.value,
            invalidSymbolsPasswordException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidEmailException::class)
    fun invalidEmail(invalidEmailException: InvalidEmailException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.INVALID_PASSWORD_SYMBOLS.value,
            invalidEmailException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }
}
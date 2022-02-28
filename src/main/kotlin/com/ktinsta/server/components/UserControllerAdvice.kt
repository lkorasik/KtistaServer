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
    fun usernameUnavailable(exception: UsernameUnavailableException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.USERNAME_UNAVAILABLE.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(EmailUnavailableException::class)
    fun emailUnavailable(emailUnavailableException: EmailUnavailableException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.EMAIL_UNAVAILABLE.value,
            emailUnavailableException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidUsernameException::class)
    fun invalidUsernameException(exception: InvalidUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.INVALID_USERNAME.value, exception.message)

        return ResponseEntity.badRequest().body(res)
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordException(exception: InvalidPasswordException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.INVALID_PASSWORD.value, exception.message)

        return ResponseEntity.badRequest().body(res)
    }

    @ExceptionHandler(InvalidUserIdException::class)
    fun invalidUserId(exception: InvalidUserIdException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.INVALID_USER_ID.value, exception.message)

        return ResponseEntity.badRequest().body(res)
    }

    @ExceptionHandler(UserStatusEmptyException::class)
    fun userStatusEmpty(exception: UserStatusEmptyException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.EMPTY_STATUS.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(TooShortUsernameException::class)
    fun tooShortUsername(exception: TooShortUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.TOO_SHORT_USERNAME.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(TooLongUsernameException::class)
    fun tooLongUsername(exception: TooLongUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.TOO_LONG_USERNAME.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidSymbolsUsernameException::class)
    fun invalidSymbolsUsername(exception: InvalidSymbolsUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.INVALID_USERNAME_SYMBOLS.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(NotUniqueUsernameException::class)
    fun notUniqueUsername(exception: NotUniqueUsernameException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.USERNAME_UNAVAILABLE.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(TooShortPasswordException::class)
    fun tooShortPassword(exception: TooShortPasswordException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.TOO_SHORT_PASSWORD.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(TooLongPasswordException::class)
    fun tooLongPassword(exception: TooLongPasswordException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.TOO_LONG_PASSWORD.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidSymbolsPasswordException::class)
    fun invalidSymbolsPassword(exception: InvalidSymbolsPasswordException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.INVALID_PASSWORD_SYMBOLS.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidEmailException::class)
    fun invalidEmail(exception: InvalidEmailException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.INVALID_EMAIL.value, exception.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }
}

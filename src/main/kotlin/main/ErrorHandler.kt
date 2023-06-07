package main

import exceptions.UserNotAuthorizedException
import exceptions.WrongPasswordException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.IllegalArgumentException

@ControllerAdvice
class ErrorHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(UserNotAuthorizedException::class)
    fun handleIllegalAccessException(e: UserNotAuthorizedException): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            HttpStatus.FORBIDDEN, e.message
        )
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(WrongPasswordException::class)
    fun handleWrongPasswordException(e: WrongPasswordException): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            HttpStatus.FORBIDDEN, e.message
        )
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(IllegalAccessException::class)
    fun handleIllegalAccessException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            HttpStatus.NOT_ACCEPTABLE, e.message!!
        )
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(exceptions.IllegalArgumentException::class)
    fun handleIllegalArgumentsException(e: exceptions.IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            HttpStatus.CONFLICT, e.message
        )
        return ResponseEntity(error, error.status)
    }
}
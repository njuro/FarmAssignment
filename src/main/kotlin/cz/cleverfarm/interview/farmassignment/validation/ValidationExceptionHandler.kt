package cz.cleverfarm.interview.farmassignment.validation

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/** Logic for handling user input which did not pass business validation. */
@RestControllerAdvice
class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentValidationException(ex: MethodArgumentNotValidException): ValidationErrorResponse {
        return ValidationErrorResponse(
            ex.bindingResult.fieldErrors
                .associateBy({ it.field }, { it.defaultMessage })
        )
    }

    @ExceptionHandler(FormValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleFormValidationException(ex: FormValidationException): ValidationErrorResponse {
        return ValidationErrorResponse(mapOf((ex.field ?: OBJECT_ERROR) to ex.message))
    }
}


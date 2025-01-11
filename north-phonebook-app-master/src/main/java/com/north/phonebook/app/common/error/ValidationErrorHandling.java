package com.north.phonebook.app.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation Error Handling.
 */
@ControllerAdvice
public class ValidationErrorHandling {

    /**
     * Handle exception of type {@link MethodArgumentNotValidException}.
     *
     * @param ex the exception
     * @return {@link ResponseEntity} of {@link ErrorResponse}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> methodArgumentNotValid(final MethodArgumentNotValidException ex) {

        final List<ErrorResponse> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorResponse(Error.INVALID_PAYLOAD_VALIDATION.getErrorCode(), fieldError.getField()));
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

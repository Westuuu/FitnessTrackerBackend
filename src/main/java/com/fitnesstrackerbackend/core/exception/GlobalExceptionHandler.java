package com.fitnesstrackerbackend.core.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        Instant.now(),
                        request.getDescription(false).replace("uri=", "")
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleBusinessLogicException(BusinessLogicException bx, WebRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        bx.getMessage(),
                        Instant.now(),
                        request.getDescription(false).replace("uri=", "")
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }


        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ax, WebRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Authentication failed",
                        Instant.now(),
                        request.getDescription(false).replace("uri=", "")
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException adx, WebRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpStatus.FORBIDDEN.value(),
                        "Access denied",
                        Instant.now(),
                        request.getDescription(false).replace("uri=", "")
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException dex, WebRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "Record already exists",
                        Instant.now(),
                        request.getDescription(false).replace("uri=", "")
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
                String detailedMessage = ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.joining("; "));

                ErrorResponse errorResponse = new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed: " + detailedMessage,
                        Instant.now(),
                        request.getDescription(false).replace("uri=", "")
                );

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Unexpected server error occurred",
                        Instant.now(),
                        request.getDescription(false).replace("uri=", "")
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

package com.fitnesstrackerbackend.domain.auth.exception;

import com.fitnesstrackerbackend.core.exception.BusinessLogicException;

public class UserAlreadyExistsException extends BusinessLogicException {
    public UserAlreadyExistsException(String email) {
        super("User with email: " + email + " already exists");
    }
}

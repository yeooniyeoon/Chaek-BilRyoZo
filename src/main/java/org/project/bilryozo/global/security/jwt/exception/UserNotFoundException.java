package org.project.bilryozo.global.security.jwt.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.LoginErrorCode;

public class UserNotFoundException extends ChaekBilRyoZoException {
    public UserNotFoundException() {
        super(LoginErrorCode.USER_NOT_FOUND);
    }
}
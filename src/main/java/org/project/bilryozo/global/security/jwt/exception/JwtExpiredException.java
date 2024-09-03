package org.project.bilryozo.global.security.jwt.exception;

import org.project.bilryozo.global.exception.ErrorCode;
import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.LoginErrorCode;

public class JwtExpiredException extends ChaekBilRyoZoException {

    public JwtExpiredException(ErrorCode errorCode) {
        super(LoginErrorCode.JWT_EXPIRED);
    }
}


package org.project.bilryozo.global.security.jwt.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.LoginErrorCode;

public class RefreshTokenNotFoundException extends ChaekBilRyoZoException {
    public RefreshTokenNotFoundException() {
        super(LoginErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}

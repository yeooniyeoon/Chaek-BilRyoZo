package org.project.bilryozo.domain.users.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.UserErrorCode;

public class AccessForbiddenException extends ChaekBilRyoZoException {
    public AccessForbiddenException() {
        super(UserErrorCode.ACCESS_FORBIDDEN);
    }
}

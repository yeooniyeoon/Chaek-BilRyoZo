package org.project.bilryozo.domain.users.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.UserErrorCode;

public class UserDuplicateEmailException extends ChaekBilRyoZoException {
    public UserDuplicateEmailException() {
        super(UserErrorCode.DUPLICATE_EMAIL);
    }
}

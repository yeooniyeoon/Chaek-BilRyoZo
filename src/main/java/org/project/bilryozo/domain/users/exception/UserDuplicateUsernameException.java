package org.project.bilryozo.domain.users.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.UserErrorCode;

public class UserDuplicateUsernameException extends ChaekBilRyoZoException {
    public UserDuplicateUsernameException() {
        super(UserErrorCode.DUPLICATE_USERNAME);
    }
}

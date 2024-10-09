package org.project.bilryozo.domain.rent.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.RentErrorCode;

public class AlreadyReturnException extends ChaekBilRyoZoException {
    public AlreadyReturnException() {
        super(RentErrorCode.ALREADY_RETURN);
    }
}

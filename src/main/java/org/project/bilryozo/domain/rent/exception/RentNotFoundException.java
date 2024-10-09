package org.project.bilryozo.domain.rent.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.RentErrorCode;

public class RentNotFoundException extends ChaekBilRyoZoException {
    public RentNotFoundException() {
        super(RentErrorCode.RENT_NOT_FOUND);
    }
}

package org.project.bilryozo.domain.rent.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.RentErrorCode;

public class InvalidRentAvailableDateException extends ChaekBilRyoZoException {
    public InvalidRentAvailableDateException() {
        super(RentErrorCode.INVALID_RENT_AVAILABLE_DATE);
    }
}

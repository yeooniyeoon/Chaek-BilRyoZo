package org.project.bilryozo.domain.rent.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.RentErrorCode;

public class BookAlreadyBorrowException extends ChaekBilRyoZoException {
    public BookAlreadyBorrowException() {
        super(RentErrorCode.BOOK_ALREADY_BORROW);
    }
}

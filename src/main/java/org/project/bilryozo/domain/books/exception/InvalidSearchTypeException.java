package org.project.bilryozo.domain.books.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.BookErrorCode;

public class InvalidSearchTypeException extends ChaekBilRyoZoException {
    public InvalidSearchTypeException() {
        super(BookErrorCode.INVALID_SEARCH_TYPE);
    }
}

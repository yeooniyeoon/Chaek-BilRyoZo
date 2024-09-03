package org.project.bilryozo.domain.books.exception;

import org.project.bilryozo.global.exception.ChaekBilRyoZoException;
import org.project.bilryozo.global.exception.domainErrorCode.BookErrorCode;

public class BookNotFoundException extends ChaekBilRyoZoException {
    public BookNotFoundException() {
        super(BookErrorCode.BOOK_NOT_FOUND);
    }
}

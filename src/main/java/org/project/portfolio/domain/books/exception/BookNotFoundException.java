package org.project.portfolio.domain.books.exception;

import org.project.portfolio.global.exception.ChaekBilRyoZoException;
import org.project.portfolio.global.exception.ErrorCode;
import org.project.portfolio.global.exception.domainErrorCode.BookErrorCode;

public class BookNotFoundException extends ChaekBilRyoZoException {
    public BookNotFoundException() {
        super(BookErrorCode.BOOK_NOT_FOUND);
    }
}

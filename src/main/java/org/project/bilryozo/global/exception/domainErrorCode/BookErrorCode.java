package org.project.bilryozo.global.exception.domainErrorCode;

import lombok.RequiredArgsConstructor;
import org.project.bilryozo.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BookErrorCode implements ErrorCode {
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "도서가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

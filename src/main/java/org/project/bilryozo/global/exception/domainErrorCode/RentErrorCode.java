package org.project.bilryozo.global.exception.domainErrorCode;

import lombok.RequiredArgsConstructor;
import org.project.bilryozo.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum RentErrorCode implements ErrorCode {
    INVALID_RENT_AVAILABLE_DATE(HttpStatus.BAD_REQUEST, "대출 가능일이 유효하지 않습니다."),
    BOOK_ALREADY_BORROW(HttpStatus.CONFLICT, "이미 대출 중인 도서입니다.");

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

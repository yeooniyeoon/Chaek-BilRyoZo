package org.project.bilryozo.global.exception.domainErrorCode;

import lombok.RequiredArgsConstructor;
import org.project.bilryozo.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 사용자가 존재합니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일이 존재합니다."),
    ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN, "실행 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

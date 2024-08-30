package org.project.portfolio.domain.books.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.books.dto.request.CreateBooksRequestDto;
import org.project.portfolio.domain.books.dto.response.BookResponse;
import org.project.portfolio.domain.books.service.BooksService;
import org.project.portfolio.domain.users.dto.response.MessageResponseDto;
import org.project.portfolio.global.exception.dto.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BooksController {
    private final BooksService booksService;

    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResponse<MessageResponseDto>> createBook(
            @RequestBody CreateBooksRequestDto dto,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servletRequest.getServletPath(),
                        booksService.createBook(dto)
                ));
    }

    // @PathVariable 사용 이유.
    // RESTful API 디자인에서는 자원의 식별자를 경로에 포함시키는 것이 관례.
    // API가 더 직관적이고 명확하게 표현됨.
    @GetMapping("/read/{id}")
    public ResponseEntity<ApiSuccessResponse<BookResponse>> readBook(
            @PathVariable("id") Long id,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servletRequest.getServletPath(),
                        booksService.readBook(id)
                ));
    }
}

package org.project.bilryozo.domain.books.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bilryozo.domain.books.dto.request.CreateBookRequestDto;
import org.project.bilryozo.domain.books.dto.request.UpdateBookRequestDto;
import org.project.bilryozo.domain.books.dto.response.BookResponse;
import org.project.bilryozo.domain.books.service.BooksService;
import org.project.bilryozo.domain.users.dto.response.MessageResponseDto;
import org.project.bilryozo.global.exception.dto.ApiSuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            @RequestBody CreateBookRequestDto dto,
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
    @GetMapping("/{id}")
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

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<Page<BookResponse>>> readAllBooks(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword,
            @PageableDefault(page = 0) Pageable pageable,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servletRequest.getServletPath(),
                        booksService.readAllBooks(type, keyword, pageable)
                ));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiSuccessResponse<Page<BookResponse>>> readPopularBooks(
            @RequestParam(required = true) int rentCount,
            @PageableDefault(page = 0) Pageable pageable,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servletRequest.getServletPath(),
                        booksService.readBooksByRentCount(rentCount, pageable)
                ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<MessageResponseDto>> updateBook(
            @PathVariable("id") Long id,
            @RequestBody UpdateBookRequestDto dto,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servletRequest.getServletPath(),
                        booksService.updateBook(id, dto)
                ));
    }
}

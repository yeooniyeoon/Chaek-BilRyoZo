package org.project.bilryozo.domain.books.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bilryozo.domain.books.domain.Books;
import org.project.bilryozo.domain.books.dto.request.CreateBookRequestDto;
import org.project.bilryozo.domain.books.dto.request.UpdateBookRequestDto;
import org.project.bilryozo.domain.books.dto.response.BookResponse;
import org.project.bilryozo.domain.books.exception.BookNotFoundException;
import org.project.bilryozo.domain.books.exception.InvalidSearchTypeException;
import org.project.bilryozo.domain.books.repository.BooksRepository;
import org.project.bilryozo.domain.users.domain.Users;
import org.project.bilryozo.domain.users.domain.UsersRole;
import org.project.bilryozo.domain.users.dto.response.MessageResponseDto;
import org.project.bilryozo.domain.users.exception.AccessForbiddenException;
import org.project.bilryozo.domain.users.repository.UsersRepository;
import org.project.bilryozo.domain.users.service.UsersService;
import org.project.bilryozo.global.security.jwt.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;
    private final UsersService usersService;

    public MessageResponseDto createBook(CreateBookRequestDto dto) {
        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        booksRepository.save(Books.builder()
                .isbn(dto.getIsbn())
                .userId(user)
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publisher(dto.getPublisher())
                .publishedDate(dto.getPublishedDate())
                .status(true)
                .rentCount(0L)
                .createdAt(LocalDateTime.now())
                .build());

        return new MessageResponseDto("도서 등록이 완료되었습니다.");
    }

    public BookResponse readBook(Long id) {
        return BookResponse.fromEntity(
                booksRepository.findById(id)
                        .orElseThrow(BookNotFoundException::new));
    }

    public Page<BookResponse> readAllBooks(String type, String keyword, Pageable pageable) {
        // 파라미터에 type, keyword가 있는 경우 검색 결과 반환
        if (type != null && keyword != null)
            return filterBooks(type, keyword, pageable)
                    .map(BookResponse::fromEntity);

        // 파라미터가 없는 경우 전체 조회
        else
            return booksRepository.findAll(pageable)
                    .map(BookResponse::fromEntity);
    }

    public Page<Books> filterBooks(String type, String keyword, Pageable pageable) {
        switch (type) {
            case "isbn":
                return booksRepository.findByIsbnContaining(keyword, pageable);
            case "title":
                return booksRepository.findByTitleContaining(keyword, pageable);
            case "author":
                return booksRepository.findByAuthorContaining(keyword, pageable);
            case "publisher":
                return booksRepository.findByPublisherContaining(keyword, pageable);
            default:
                throw new InvalidSearchTypeException();
        }
    }

    public Page<BookResponse> readBooksByRentCount(int rentCount, Pageable pageable) {
        return booksRepository.findByRentCountGreaterThan(rentCount, pageable)
                .map(BookResponse::fromEntity);
    }

    public MessageResponseDto updateBook(Long id, UpdateBookRequestDto dto) {
        Users user = usersService.getAuthenticateduser();

        if (user.getRole() != UsersRole.ADMIN)
            throw new AccessForbiddenException();

        Books book = booksRepository.findById(id)
                        .orElseThrow(BookNotFoundException::new);

        book.updateBook(dto, user);
        booksRepository.save(book);

        return new MessageResponseDto("도서 수정이 완료되었습니다.");
    }
}
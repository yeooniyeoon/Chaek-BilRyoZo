package org.project.bilryozo.domain.books.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bilryozo.domain.books.domain.Books;
import org.project.bilryozo.domain.books.dto.request.CreateBooksRequestDto;
import org.project.bilryozo.domain.books.dto.response.BookResponse;
import org.project.bilryozo.domain.books.exception.BookNotFoundException;
import org.project.bilryozo.domain.books.repository.BooksRepository;
import org.project.bilryozo.domain.users.domain.Users;
import org.project.bilryozo.domain.users.dto.response.MessageResponseDto;
import org.project.bilryozo.domain.users.repository.UsersRepository;
import org.project.bilryozo.global.security.jwt.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;

    public MessageResponseDto createBook(CreateBooksRequestDto dto) {
        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        booksRepository.save(Books.builder()
                .isbn(dto.getIsbn())
                .userId(user)
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publisher(dto.getPublisher())
                .publishDate(dto.getPublishedDate())
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
        int page = pageable.getPageNumber();
        int limit = pageable.getPageSize();

        if (type != null && keyword != null) {
            Page<Books> books;
            switch (type) {
                case "isbn":
                    books = booksRepository.findByIsbnContaining(keyword, PageRequest.of(page, limit));
                    break;
                case "title":
                    books = booksRepository.findByTitleContaining(keyword, PageRequest.of(page, limit));
                    break;
                case "author":
                    books = booksRepository.findByAuthorContaining(keyword, PageRequest.of(page, limit));
                    break;
                case "publisher":
                    books = booksRepository.findByPublisherContaining(keyword, PageRequest.of(page, limit));
                    break;
                default:
                    return Page.empty();
            }
            return books.map(BookResponse::fromEntity);
        }
        else {
            Page<Books> books = booksRepository.findAll(PageRequest.of(page, limit));
            return books.map(BookResponse::fromEntity);
        }
    }
}
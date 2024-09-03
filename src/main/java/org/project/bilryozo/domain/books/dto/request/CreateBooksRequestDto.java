package org.project.bilryozo.domain.books.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.project.bilryozo.domain.books.domain.Books;
import org.project.bilryozo.domain.users.domain.Users;
import org.project.bilryozo.domain.users.repository.UsersRepository;

import java.time.LocalDate;

@Getter
@Builder
public class CreateBooksRequestDto {
    private Long isbn;
    private Long userId;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishedDate;

    public static Books toEntity(CreateBooksRequestDto dto, Users users) {
        return Books.builder()
                .isbn(dto.isbn)
                .title(dto.title)
                .userId(users)
                .author(dto.author)
                .publisher(dto.publisher)
                .publishDate(dto.publishedDate)
                .build();
    }
}

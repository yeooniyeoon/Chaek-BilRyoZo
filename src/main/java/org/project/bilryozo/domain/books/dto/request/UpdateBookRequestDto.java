package org.project.bilryozo.domain.books.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.project.bilryozo.domain.books.domain.Books;

import java.time.LocalDate;

@Getter
@Builder
public class UpdateBookRequestDto {
    private Long isbn;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishedDate;

    public static Books toEntity(UpdateBookRequestDto dto) {
        return Books.builder()
                .isbn(dto.isbn)
                .title(dto.title)
                .author(dto.author)
                .publisher(dto.publisher)
                .publishedDate(dto.publishedDate)
                .build();
    }
}

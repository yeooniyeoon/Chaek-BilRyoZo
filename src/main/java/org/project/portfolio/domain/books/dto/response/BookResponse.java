package org.project.portfolio.domain.books.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.project.portfolio.domain.books.domain.Books;

import java.awt.print.Book;
import java.time.LocalDate;

@Getter
@Builder
public class BookResponse {
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishedDate;
    private Boolean status;

    public static BookResponse fromEntity(Books book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishedDate(book.getPublishDate())
                .status(book.getStatus())
                .build();
    }
}

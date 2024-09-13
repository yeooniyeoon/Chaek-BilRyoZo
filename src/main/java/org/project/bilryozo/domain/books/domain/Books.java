package org.project.bilryozo.domain.books.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.project.bilryozo.domain.books.dto.request.UpdateBookRequestDto;
import org.project.bilryozo.domain.users.domain.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Where(clause = "deleted_at is null")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long isbn;

    @ManyToOne
    @JoinColumn(name = "user_id")   // 도서를 등록한 관리자 id
    private Users userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @Column    // true: 대출 가능, false: 대출 불가
    private Boolean status;

    @Column(nullable = false, name = "rent_count")
    private Long rentCount;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")   // 도서를 마지막으로 수정한 관리자 id
    private Long updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")   // 도서를 삭제한 관리자 id
    private Long deletedBy;

    public void updateBook(UpdateBookRequestDto dto, Users user) {
        if (dto.getIsbn() != null)
            this.isbn = dto.getIsbn();

        if (dto.getTitle() != null)
            this.title = dto.getTitle();

        if (dto.getAuthor() != null)
            this.author = dto.getAuthor();

        if (dto.getPublisher() != null)
            this.publisher = dto.getPublisher();

        if (dto.getPublishedDate() != null)
            this.publishedDate = dto.getPublishedDate();

        this.updatedAt = LocalDateTime.now();
        this.updatedBy = user.getId();
    }

    public void deleteBook(Users user) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = user.getId();
    }
}

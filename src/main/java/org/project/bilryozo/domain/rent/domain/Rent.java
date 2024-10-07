package org.project.bilryozo.domain.rent.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.boot.model.naming.Identifier;
import org.project.bilryozo.domain.books.domain.Books;
import org.project.bilryozo.domain.users.domain.Users;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books bookId;

    @Column(nullable = false, name = "rent_date")
    private LocalDate rentDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(nullable = false)   // 반납 여부. true인 경우 반납 완료, false인 경우 미반납.
    private boolean status;
}

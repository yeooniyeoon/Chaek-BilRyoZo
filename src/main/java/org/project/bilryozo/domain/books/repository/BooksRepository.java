package org.project.bilryozo.domain.books.repository;

import org.project.bilryozo.domain.books.domain.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    @Query("SELECT b FROM Books b WHERE CAST(b.isbn AS string) LIKE %:keyword% AND b.deletedAt IS NULL")
    Page<Books> findByIsbnContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Books b WHERE CAST(b.title AS string) LIKE %:keyword% AND b.deletedAt IS NULL")
    Page<Books> findByTitleContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Books b WHERE CAST(b.author AS string) LIKE %:keyword% AND b.deletedAt IS NULL")
    Page<Books> findByAuthorContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Books b WHERE CAST(b.publisher AS string) LIKE %:keyword% AND b.deletedAt IS NULL")
    Page<Books> findByPublisherContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Books b WHERE b.rentCount > :rentCount AND b.deletedAt IS NULL")
    Page<Books> findByRentCountGreaterThan(@Param("rentCount") int rentCount, Pageable pageable);
}

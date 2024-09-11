package org.project.bilryozo.domain.books.repository;

import org.project.bilryozo.domain.books.domain.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    Page<Books> findByIsbnContaining(String keyword, Pageable pageable);
    Page<Books> findByTitleContaining(String keyword, Pageable pageable);
    Page<Books> findByAuthorContaining(String keyword, Pageable pageable);
    Page<Books> findByPublisherContaining(String keyword, Pageable pageable);
    Page<Books> findByRentCountGreaterThan(int rentCount, Pageable pageable);
}

package org.project.bilryozo.domain.books.repository;

import org.project.bilryozo.domain.books.domain.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    Page<Books> findByIsbnContaining(String keyword, PageRequest of);
    Page<Books> findByTitleContaining(String keyword, PageRequest of);
    Page<Books> findByAuthorContaining(String keyword, PageRequest of);
    Page<Books> findByPublisherContaining(String keyword, PageRequest of);
}

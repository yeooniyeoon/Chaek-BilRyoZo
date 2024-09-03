package org.project.bilryozo.domain.books.repository;

import org.project.bilryozo.domain.books.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

}

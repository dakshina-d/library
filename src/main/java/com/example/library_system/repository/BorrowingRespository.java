package com.example.library_system.repository;

import com.example.library_system.entity.Book;
import com.example.library_system.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRespository extends JpaRepository<Borrowing , Long> {
    Borrowing findByBookAndReturnDateIsNull(Book book);

    Borrowing findByBook(Book book);
}

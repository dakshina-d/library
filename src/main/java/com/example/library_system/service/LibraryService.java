package com.example.library_system.service;

import com.example.library_system.domain.BookRequest;
import com.example.library_system.domain.BorrowerRequest;
import com.example.library_system.entity.Borrower;
import com.example.library_system.mapper.ResponseGenerator;
import org.springframework.http.ResponseEntity;

public interface LibraryService {

    ResponseEntity<Object> registerBook(BookRequest bookRequest) throws Exception;

    ResponseEntity<Object> registerBorrower(BorrowerRequest borrowerRequest) throws Exception;

    ResponseEntity<Object> getAllBooks() throws Exception;

    ResponseEntity<Object> borrowBook(Long borrowerId, Long bookId) throws Exception;

    ResponseEntity<Object> returnBook(Long bookId) throws Exception;
}

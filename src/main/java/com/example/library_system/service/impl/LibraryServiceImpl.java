package com.example.library_system.service.impl;

import com.example.library_system.domain.BookRequest;
import com.example.library_system.domain.BorrowerRequest;
import com.example.library_system.entity.Book;
import com.example.library_system.entity.Borrower;
import com.example.library_system.entity.Borrowing;
import com.example.library_system.mapper.ResponseGenerator;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.repository.BorrowerRepository;
import com.example.library_system.repository.BorrowingRespository;
import com.example.library_system.service.LibraryService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class LibraryServiceImpl implements LibraryService {

    private BookRepository bookRepository;
    private BorrowerRepository borrowerRepository;
    private BorrowingRespository borrowingRespository;

    private ResponseGenerator responseGenerator;

    private ModelMapper modelMapper;

    public LibraryServiceImpl(BookRepository bookRepository, BorrowerRepository borrowerRepository, BorrowingRespository borrowingRespository, ResponseGenerator responseGenerator, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.borrowingRespository = borrowingRespository;
        this.responseGenerator = responseGenerator;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> registerBook(BookRequest bookRequest) throws Exception {

        Book checkBook = bookRepository.findTopByIsbn(bookRequest.getIsbn());

        if(Objects.nonNull(checkBook)){
            if(!checkBook.getAuthor().equals(bookRequest.getAuthor())){
                return responseGenerator.generateSuccessResponse(HttpStatus.OK, "01", "Author cannot be changed for the same isbn", new Object[]{});
            }
            if(!checkBook.getTitle().equals(bookRequest.getTitle())){
                return responseGenerator.generateSuccessResponse(HttpStatus.OK, "01", "Title cannot be changed for the same isbn", new Object[]{});
            }
        }
        Book book = modelMapper.map(bookRequest, Book.class);
        bookRepository.save(book);
        return responseGenerator.generateSuccessResponse(HttpStatus.OK, "00", "Success", book);
    }

    @Override
    public ResponseEntity<Object> registerBorrower(BorrowerRequest borrowerRequest) throws Exception {

        Borrower checkBorrower = borrowerRepository.findByEmail(borrowerRequest.getEmail());
        if(Objects.nonNull(checkBorrower)){
            return responseGenerator.generateSuccessResponse(HttpStatus.OK, "01", "The email id is already registered", new Object[]{});
        }
        Borrower borrower = modelMapper.map(borrowerRequest, Borrower.class);
        borrowerRepository.save(borrower);
        return responseGenerator.generateSuccessResponse(HttpStatus.OK, "00", "Success", borrower);
    }

    @Override
    public ResponseEntity<Object> getAllBooks() throws Exception {

        List<Book> bookList = bookRepository.findAll();
        return responseGenerator.generateSuccessResponse(HttpStatus.OK, "00", "Success", bookList);
    }

    @Override
    public ResponseEntity<Object> borrowBook(Long borrowerId, Long bookId) throws Exception {

        Book book = bookRepository.findById(bookId).orElseThrow(
                ()-> new EntityNotFoundException("Not found a book by the given ID")
        );

        Borrowing checkBorrowing = borrowingRespository.findByBookAndReturnDateIsNull(book);
        if(Objects.nonNull(checkBorrowing)){
            return responseGenerator.generateSuccessResponse(HttpStatus.OK, "01", "Cannot borrow this book", book);
        }
        Borrower borrower = borrowerRepository.findById(borrowerId).orElseThrow(
                ()-> new EntityNotFoundException("Not found the borrower")
        );

        Borrowing saveBorrowing = new Borrowing();
        saveBorrowing.setBorrower(borrower);
        saveBorrowing.setBook(book);
        saveBorrowing.setBorrowDate(LocalDate.now());
        Borrowing savedBorrowing = borrowingRespository.save(saveBorrowing);
        return responseGenerator.generateSuccessResponse(HttpStatus.OK, "00", "Success", savedBorrowing);
    }

    @Override
    public ResponseEntity<Object> returnBook(Long bookId) throws Exception {

        Book book = bookRepository.findById(bookId).orElseThrow(
                ()-> new EntityNotFoundException("Not foud the book")
        );
        Borrowing checkBorrowing = borrowingRespository.findByBook(book);
        if(Objects.isNull(checkBorrowing)){
            return responseGenerator.generateSuccessResponse(HttpStatus.OK, "01", "Not borrowed this book", book);
        }
        if(checkBorrowing.getReturnDate() != null){
            return responseGenerator.generateSuccessResponse(HttpStatus.OK, "01", "Already returned", book);
        }

        checkBorrowing.setReturnDate(LocalDate.now());
        borrowingRespository.save(checkBorrowing);

        return responseGenerator.generateSuccessResponse(HttpStatus.OK, "00", "Success", checkBorrowing);
    }


}

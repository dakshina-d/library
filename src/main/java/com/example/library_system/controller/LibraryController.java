package com.example.library_system.controller;

import com.example.library_system.domain.BookRequest;
import com.example.library_system.domain.BorrowerRequest;
import com.example.library_system.dto.BookRequestDto;
import com.example.library_system.dto.BorrowerRequestDto;
import com.example.library_system.service.LibraryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryController {

    private LibraryService libraryService;

    private ModelMapper modelMapper;

    @PostMapping("/books")
    public ResponseEntity<Object> registerBook(@RequestBody BookRequestDto bookRequestDto) throws Exception{
        return libraryService.registerBook(modelMapper.map(bookRequestDto, BookRequest.class));
    }

    @PostMapping("/createBorrower")
    public ResponseEntity<Object> registerBorrower(@RequestBody BorrowerRequestDto borrowerRequestDto) throws Exception{
        return libraryService.registerBorrower(modelMapper.map(borrowerRequestDto, BorrowerRequest.class));
    }

    @GetMapping("/books")
    public ResponseEntity<Object> getAllBooks() throws Exception{
        return libraryService.getAllBooks();
    }

    @PostMapping("/borrow/{borrowerId}/{bookId}")
    public ResponseEntity<Object> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) throws Exception{
        return libraryService.borrowBook(borrowerId, bookId);
    }

    @PostMapping("return/{bookId}")
    public ResponseEntity<Object> returnBook(@PathVariable Long bookId) throws Exception{
        return libraryService.returnBook(bookId);
    }

}

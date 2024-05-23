package com.example.library_system.service;

import com.example.library_system.domain.BookRequest;
import com.example.library_system.dto.ResponseDto;
import com.example.library_system.entity.Book;
import com.example.library_system.mapper.ResponseGenerator;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LibraryServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ResponseGenerator responseGenerator;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LibraryServiceImpl libraryServiceImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRegisterBookSuccess() throws Exception{
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("Dakshina");
        bookRequest.setId(1L);
        bookRequest.setIsbn("1010");
        bookRequest.setTitle("My Title");

        Book book = new Book(1L, "1010", "My Title", "Dakshina");

        when(bookRepository.findTopByIsbn(bookRequest.getIsbn())).thenReturn(null);
        when(modelMapper.map(bookRequest, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(responseGenerator.generateSuccessResponse(HttpStatus.OK, "00", "Success", book)).
                thenReturn(new ResponseEntity<>(new ResponseDto("00", "Success", book), HttpStatus.OK));


        ResponseEntity<Object> actualRegisterBookResult = libraryServiceImpl.registerBook(bookRequest);
        ResponseDto responseDto = (ResponseDto) actualRegisterBookResult.getBody();
        assertTrue(responseDto.getData() instanceof Book);
        assertEquals("00", responseDto.getResponseCode());
        assertEquals("My Title", ((Book) responseDto.getData()).getTitle());
        assertEquals("1010", ((Book) responseDto.getData()).getIsbn());
        assertEquals("Dakshina", ((Book) responseDto.getData()).getAuthor());
        assertEquals("Success", responseDto.getResponseDescription());
        assertEquals(200, actualRegisterBookResult.getStatusCodeValue());
        assertTrue(actualRegisterBookResult.hasBody());
        assertTrue(actualRegisterBookResult.getHeaders().isEmpty());

    }

}

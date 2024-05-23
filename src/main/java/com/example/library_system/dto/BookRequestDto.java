package com.example.library_system.dto;

import lombok.Data;

@Data
public class BookRequestDto {

    private Long id;

    private String isbn;

    private String title;

    private String author;

}

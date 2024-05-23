package com.example.library_system.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class BorrowerRequestDto {

    private String name;

    @Email(message = "Email is not valid")
    private String email;

}

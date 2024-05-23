package com.example.library_system.exception;

import com.example.library_system.mapper.ResponseGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandler {

    private ResponseGenerator responseGenerator;

    public ResponseEntity<?> handleException(Exception e){
        return responseGenerator.generateSuccessResponse(HttpStatus.BAD_REQUEST,"01", e.getMessage(), new Object[]{});
    }

}

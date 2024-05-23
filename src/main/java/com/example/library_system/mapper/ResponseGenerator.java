package com.example.library_system.mapper;

import com.example.library_system.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ResponseGenerator {

    private ModelMapper modelMapper;

    public ResponseEntity<Object> generateSuccessResponse(HttpStatus httpStatus,
                                                          String responseCode,
                                                          String responseDescription,
                                                          Object dataObject){

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ResponseDto responseDto = new ResponseDto();

        responseDto.setResponseCode(responseCode);
        responseDto.setResponseDescription(responseDescription);
        responseDto.setData(dataObject);

        return ResponseEntity.status(httpStatus).body(responseDto);

    }

}

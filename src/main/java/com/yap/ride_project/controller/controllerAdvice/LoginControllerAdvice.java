package com.yap.ride_project.controller.controllerAdvice;

import com.yap.ride_project.dto.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> duplicationId(DataIntegrityViolationException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.CONFLICT)
                .errorMsg("UID 중복").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> jsonParsingError(HttpMessageNotReadableException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.CONFLICT)
                .errorMsg("JSON 파싱 오류.").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> dtoValidationFail(MethodArgumentNotValidException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.CONFLICT)
                .errorMsg("필드 제약조건 위반").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    private static HttpHeaders getHttpHeader() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");
        return responseHeaders;
    }
}

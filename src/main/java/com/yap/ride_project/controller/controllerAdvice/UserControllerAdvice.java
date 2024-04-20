package com.yap.ride_project.controller.controllerAdvice;

import com.yap.ride_project.dto.request.ErrorResponseDTO;
import com.yap.ride_project.exception.NotSuchUserException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> duplicationId(DataIntegrityViolationException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.CONFLICT)
                .errorMsg("UID 중복").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> jsonParsingError(HttpMessageNotReadableException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.BAD_REQUEST)
                .errorMsg("JSON 파싱 오류.").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> dtoValidationFail(MethodArgumentNotValidException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.UNPROCESSABLE_ENTITY)
                .errorMsg("필드 제약조건 위반").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(NotSuchUserException.class)
    public ResponseEntity<ErrorResponseDTO> noSuchUser(NotSuchUserException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.NOT_FOUND)
                .errorMsg("id에 해당하는 유저 없음").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.NOT_FOUND);
    }
    private static HttpHeaders getHttpHeader() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");
        return responseHeaders;
    }
}

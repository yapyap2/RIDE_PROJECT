package com.yap.ride_project.controller.controllerAdvice;

import com.yap.ride_project.User;
import com.yap.ride_project.dto.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class LoginControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> duplicationId(DataIntegrityViolationException e){

        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.CONFLICT)
                .errorMsg("UID 중복").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.CONFLICT);
    }

    private static HttpHeaders getHttpHeader() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");
        return responseHeaders;
    }
}

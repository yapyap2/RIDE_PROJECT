package com.yap.ride_project.controller.controllerAdvice;

import com.yap.ride_project.dto.request.ErrorResponseDTO;
import com.yap.ride_project.exception.BikeTypeParsingException;
import com.yap.ride_project.exception.NotSuchRideException;
import com.yap.ride_project.exception.RideQueryParameterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RideControllerAdvice {

    @ExceptionHandler(NotSuchRideException.class)
    public ResponseEntity<ErrorResponseDTO> notSuchRide(NotSuchRideException e){
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.NOT_FOUND)
                .errorMsg("id에 해당하는 라이드 없음").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BikeTypeParsingException.class)
    public ResponseEntity<ErrorResponseDTO> bikeTypeParsingError(BikeTypeParsingException e){
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.UNPROCESSABLE_ENTITY)
                .errorMsg("자전거 타입 파싱 에러").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RideQueryParameterException.class)
    public ResponseEntity<ErrorResponseDTO> rideQueryParameterException(RideQueryParameterException e){
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.UNPROCESSABLE_ENTITY)
                .errorMsg("검색 쿼리파라미터 파싱 에러").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    private static HttpHeaders getHttpHeader() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");
        return responseHeaders;
    }
}

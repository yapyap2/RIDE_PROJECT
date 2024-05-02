package com.yap.ride_project.controller.controllerAdvice;

import com.yap.ride_project.dto.request.ErrorResponseDTO;
import com.yap.ride_project.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.BAD_REQUEST)
                .errorMsg("자전거 타입 파싱 에러").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RideQueryParameterException.class)
    public ResponseEntity<ErrorResponseDTO> rideQueryParameterException(RideQueryParameterException e){
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.BAD_REQUEST)
                .errorMsg("검색 쿼리파라미터 파싱 에러").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> rideQueryParameterException(MethodArgumentTypeMismatchException e){
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.BAD_REQUEST)
                .errorMsg("검색 쿼리파라미터 바인딩 에러. 데이터타입이 잘못됨").exceptionMsg("필드명: " + e.getName() + " 파라미터 값: " + e.getValue().toString() + " 가 잘못되었습니다.").build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RelationTypeMappingException.class)
    public ResponseEntity<ErrorResponseDTO> relationRegisterTypeError(RelationTypeMappingException e){
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.BAD_REQUEST)
                .errorMsg("RelationType 파라미터 바인딩 실패").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RelationUpdateException.class)
    public ResponseEntity<ErrorResponseDTO> relationUpdateError(RelationUpdateException e){
        ErrorResponseDTO errorDto = ErrorResponseDTO.builder().status(HttpStatus.BAD_REQUEST)
                .errorMsg("Relation 등록 실패.").exceptionMsg(e.getMessage()).build();

        return new ResponseEntity<ErrorResponseDTO>(errorDto, getHttpHeader(), HttpStatus.BAD_REQUEST);
    }

    private static HttpHeaders getHttpHeader() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");
        return responseHeaders;
    }
}

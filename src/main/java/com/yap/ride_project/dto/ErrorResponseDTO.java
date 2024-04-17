package com.yap.ride_project.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDTO{

    private HttpStatus status;
    private String errorMsg;
    private String exceptionMsg;

    @Builder
    public ErrorResponseDTO(HttpStatus status, String errorMsg, String exceptionMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.exceptionMsg = exceptionMsg;
    }
}

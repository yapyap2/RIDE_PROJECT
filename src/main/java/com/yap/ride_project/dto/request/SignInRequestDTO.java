package com.yap.ride_project.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yap.ride_project.entity.enums.BikeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignInRequestDTO {

    @NotBlank
    private String UID;
    @NotNull
    @Size(min = 2, max = 20)
    private String name;
    @NotNull
    private int age;
    @NotNull
    private int gender;
    private float ftp;
    @JsonProperty("start_at")
    private String startAt;
    @NotNull
    @JsonProperty("location_code")
    private String locationCode;
    @JsonProperty("bike_type")
    @NotEmpty
    private List<BikeType> bikeType;

}

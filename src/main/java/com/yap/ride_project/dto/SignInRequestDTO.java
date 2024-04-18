package com.yap.ride_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yap.ride_project.entity.enums.BikeType;
import lombok.Data;

import java.time.YearMonth;
import java.util.List;

@Data
public class SignInRequestDTO {

    private String UID;
    private Long ID;
    private String name;
    private int age;
    private int gender;
    private float ftp;
    @JsonProperty("start_at")
    private String startAt;
    @JsonProperty("location_code")
    private String locationCode;
    @JsonProperty("bike_type")
    private List<BikeType> bikeType;

}

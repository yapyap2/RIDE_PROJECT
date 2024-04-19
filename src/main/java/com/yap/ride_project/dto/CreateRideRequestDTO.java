package com.yap.ride_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateRideRequestDTO {

    @JsonProperty("user_id")
    private long userId;
    @NotNull
    @Size(min = 2, max = 20)
    @JsonProperty("ride_name")
    private String rideName;
    private String description;
    @NotNull
    @JsonProperty("start_date")
    private String startDate;
    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "9999")
    private Double distance;
    @DecimalMin(value = "0")
    @DecimalMax(value = "99999")
    private Double elevation;
    @NotNull
    @JsonProperty("start_location_code")
    private String startLocationCode;

    @JsonProperty("estimate_time")
    private Integer estimateTime;
    @JsonProperty("ftp_limit")
    private Integer ftpLimit;
    @JsonProperty("age_limit")
    private Integer ageLimit;
    @JsonProperty("participant_limit")
    private Integer participantLimit;
    @JsonProperty("participant_minimum")
    private Integer participantMinimum;
    @NotNull
    @JsonProperty("bike_type")
    private List<String> bikeType;

}

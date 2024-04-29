package com.yap.ride_project.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yap.ride_project.entity.enums.BikeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;


@Data
public class UpdateUserRequestDTO {
    @NotBlank
    private String UID;
    @NotNull
    private Long id;
    private String name;
    private String description;
    private Integer age;
    private Integer gender;
    private Float ftp;
    @JsonProperty("start_at")
    private String startAt;
    @JsonProperty("location_code")
    private String locationCode;
    @JsonProperty("bike_type")
    private List<BikeType> bikeType;
}

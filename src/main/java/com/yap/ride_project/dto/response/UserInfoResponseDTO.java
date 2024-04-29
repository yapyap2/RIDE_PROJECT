package com.yap.ride_project.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.enums.BikeType;
import com.yap.ride_project.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserInfoResponseDTO {
    private long userId;
    private String name;
    private String description;
    private int age;
    private Gender gender;
    private float ftp;
    private String locationCode;
    private LocalDate startAt;

    private List<BikeType> bikeType;
}

package com.yap.ride_project.entity;

import com.yap.ride_project.entity.enums.BikeType;
import com.yap.ride_project.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(unique = true)
    private String UID;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private float ftp;
    private String locationCode;
    private LocalDate startAt;
    @ElementCollection(targetClass = BikeType.class)
    @CollectionTable(name = "BIKE_TYPE", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private List<BikeType> bikeType;


    @Builder
    public User(String UID, String name, int age, float ftp,int gender, String location_code, List<BikeType> bikeType, LocalDate startAt) {
        this.UID = UID;
        this.name = name;
        this.age = age;
        this.gender = (gender == 1) ? Gender.MALE : Gender.FEMALE;
        this.ftp = ftp;
        this.locationCode = location_code;
        this.startAt = startAt;
        this.bikeType = bikeType;
    }
}
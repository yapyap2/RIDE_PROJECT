package com.yap.ride_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yap.ride_project.dto.request.UpdateUserRequestDTO;
import com.yap.ride_project.entity.enums.BikeType;
import com.yap.ride_project.entity.enums.Gender;
import com.yap.ride_project.exception.UserStartAtParsingException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(unique = true)
    private String UID;
    private String name;
    private String description;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private float ftp;
    private String locationCode;
    private LocalDate startAt;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
    @ElementCollection(targetClass = BikeType.class)
    @CollectionTable(name = "BIKE_TYPE", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private List<BikeType> bikeType;


    @Builder
    public User(String UID, String description, String name, int age, float ftp,int gender, String location_code, List<BikeType> bikeType, String startAt) {
        this.UID = UID;
        this.name = name;
        this.description = description;
        this.age = age;
        this.gender = (gender == 0) ? Gender.MALE : Gender.FEMALE;
        this.ftp = ftp;

        this.locationCode = location_code;
        if(startAt != null){
            try{
                this.startAt = LocalDate.parse(startAt + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e){
                throw new UserStartAtParsingException("start_at 필드 형식 오류. yyyy-MM 형식이 아닙니다. value: " + startAt);
            }
        }
        this.bikeType = bikeType;
    }


    public void update(UpdateUserRequestDTO dto) {
        if (dto.getName() != null) {
            this.name = dto.getName();
        }
        if (dto.getDescription() != null) {
            this.description = dto.getDescription();
        }
        if (dto.getAge() != null) {
            this.age = dto.getAge();
        }
        if (dto.getGender() != null) {
            this.gender = (dto.getGender() == 0) ? Gender.MALE : Gender.FEMALE;
        }
        if (dto.getFtp() != null) {
            this.ftp = dto.getFtp();
        }
        if (dto.getLocationCode() != null) {
            this.locationCode = dto.getLocationCode();
        }
        if (dto.getStartAt() != null) {
            try{
                this.startAt = LocalDate.parse(dto.getStartAt() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e){
                throw new UserStartAtParsingException("start_at 필드 형식 오류. yyyy-MM 형식이 아닙니다. value: " + dto.getStartAt());
            }
        }
        if (dto.getBikeType() != null) {
            this.bikeType = dto.getBikeType();
        }
    }

}

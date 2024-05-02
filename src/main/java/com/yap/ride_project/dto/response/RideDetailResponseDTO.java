package com.yap.ride_project.dto.response;

import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.User;
import com.yap.ride_project.entity.enums.BikeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RideDetailResponseDTO {
    private Long rideId;
    private String rideName;
    private String description;
    private Double distance;
    private Double elevation;
    private String startLocationCode;
    private LocalDateTime startDate;
    private Integer estimateTime;
    private Integer ftpLimit;
    private Integer ageLimit;
    private Integer participantLimit;
    private Integer participantMinimum;
    private List<BikeType> bikeType;
    private LocalDateTime createdAt;
    private Long ownerUserId;
    private String ownerUserName;

    public RideDetailResponseDTO(Long rideId, String rideName, String description, Double distance, Double elevation, String startLocationCode, LocalDateTime startDate, Integer estimateTime, Integer ftpLimit, Integer ageLimit, Integer participantLimit, Integer participantMinimum,LocalDateTime createdAt, boolean roadbike, boolean mtb, boolean minivelo, boolean cx, boolean hybrid, boolean none, long ownerUserId, String ownerUserName) {
        this.rideId = rideId;
        this.rideName = rideName;
        this.description = description;
        this.distance = distance;
        this.elevation = elevation;
        this.startLocationCode = startLocationCode;
        this.startDate = startDate;
        this.estimateTime = estimateTime;
        this.ftpLimit = ftpLimit;
        this.ageLimit = ageLimit;
        this.participantLimit = participantLimit;
        this.participantMinimum = participantMinimum;

        this.bikeType = new ArrayList<>();
        if(roadbike) bikeType.add(BikeType.ROADBIKE);
        if(mtb) bikeType.add(BikeType.MTB);
        if(cx) bikeType.add(BikeType.CX);
        if(minivelo) bikeType.add(BikeType.MINIVELO);
        if(hybrid) bikeType.add(BikeType.HYBRID);
        if(none) bikeType.add(BikeType.NONE);


        this.createdAt = createdAt;
        this.ownerUserId = ownerUserId;
        this.ownerUserName = ownerUserName;
    }

}

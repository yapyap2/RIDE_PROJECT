package com.yap.ride_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SimpleRide {
    private Long rideId;
    private String rideName;
    private Double distance;
    private Double elevation;
    private String startLocationCode;
    private LocalDateTime startDate;
    private Long ownerUserId;
    private String ownerUserName;

    public SimpleRide(Long rideId, String rideName, Double distance, Double elevation, String startLocationCode, LocalDateTime startDate,Long ownerUserId, String ownerUserName) {
        this.rideId = rideId;
        this.rideName = rideName;
        this.distance = distance;
        this.elevation = elevation;
        this.startLocationCode = startLocationCode;
        this.startDate = startDate;
        this.ownerUserId = ownerUserId;
        this.ownerUserName = ownerUserName;
    }
}
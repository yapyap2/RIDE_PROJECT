package com.yap.ride_project.dto.response;

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
}

package com.yap.ride_project.entity;

import java.time.LocalDateTime;
public class SimpleRideQueryDSL implements SimpleRide{
    private Long rideId;
    private String rideName;
    private Double distance;
    private Double elevation;
    private String startLocationCode;
    private LocalDateTime startDate;
    private Long ownerUserId;



    @Override
    public Long getRideId() {
        return rideId;
    }

    @Override
    public String getRideName() {
        return rideName;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public Double getElevation() {
        return elevation;
    }

    @Override
    public String getStartLocationCode() {
        return startLocationCode;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public Long getOwnerUserId() {
        return ownerUserId;
    }
}

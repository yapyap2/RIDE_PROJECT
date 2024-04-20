package com.yap.ride_project.dto.response;

import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.SimpleRide;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleRideResponseDTO {

    private Long rideId;
    private String rideName;
    private Double distance;
    private Double elevation;
    private String startLocationCode;
    private LocalDateTime startDate;
    private Long ownerUserId;


    public SimpleRideResponseDTO(SimpleRide ride) {
        this.rideId = ride.getRideId();
        this.rideName = ride.getRideName();
        this.distance = ride.getDistance();
        this.elevation = ride.getElevation();
        this.startLocationCode = ride.getStartLocationCode();
        this.startDate = ride.getStartDate();
        this.ownerUserId = ride.getOwnerUserId();
    }
}

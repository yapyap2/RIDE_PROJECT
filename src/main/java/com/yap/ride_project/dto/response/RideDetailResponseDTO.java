package com.yap.ride_project.dto.response;

import com.yap.ride_project.entity.enums.BikeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
    private Long OwnerUserId;
    private String OwnerUserName;
}

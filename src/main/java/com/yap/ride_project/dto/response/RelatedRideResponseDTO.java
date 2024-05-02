package com.yap.ride_project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.yap.ride_project.entity.enums.RelationType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RelatedRideResponseDTO extends SimpleRide{

    @QueryProjection
    public RelatedRideResponseDTO(Long rideId, String rideName, Double distance, Double elevation, String startLocationCode,LocalDateTime startDate, Long ownerUserId, String ownerUserName, LocalDateTime createAt, boolean summited, boolean liked) {
        super(rideId, rideName, distance,elevation,startLocationCode,startDate, ownerUserId, ownerUserName);
        this.createAt = createAt;

        if(liked) type = RelationType.LIKE;
        if(summited) type = RelationType.SUMMIT;
    }

    private LocalDateTime createAt;
    private RelationType type;

}

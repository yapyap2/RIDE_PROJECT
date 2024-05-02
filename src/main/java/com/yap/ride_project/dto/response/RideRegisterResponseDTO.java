package com.yap.ride_project.dto.response;

import com.yap.ride_project.entity.enums.RelationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RideRegisterResponseDTO {
    private long userId;
    private long rideId;
    private String rideName;

    private RelationType relationType;
    public RideRegisterResponseDTO(long userId, long rideId, String rideName, RelationType relationType) {
        this.userId = userId;
        this.rideId = rideId;
        this.rideName = rideName;
        this.relationType = relationType;
    }
}

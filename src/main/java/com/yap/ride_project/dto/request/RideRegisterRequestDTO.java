package com.yap.ride_project.dto.request;

import com.yap.ride_project.entity.enums.RelationType;
import lombok.Data;

@Data
public class RideRegisterRequestDTO {

    private long userId;
    private long rideId;
    private RelationType relationType;
}

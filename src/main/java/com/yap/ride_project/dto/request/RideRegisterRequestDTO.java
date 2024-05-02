package com.yap.ride_project.dto.request;

import com.yap.ride_project.entity.enums.RelationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RideRegisterRequestDTO {

    private long userId;
    private long rideId;
    @Schema(description = "좋아요 등록시 LIKE, 참가 등록시 SUMMIT")
    private RelationType relationType;
}

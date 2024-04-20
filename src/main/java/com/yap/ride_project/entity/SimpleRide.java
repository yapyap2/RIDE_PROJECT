package com.yap.ride_project.entity;

import java.time.LocalDateTime;

public interface SimpleRide {
    Long getRideId();
    String getRideName();
    Double getDistance();
    Double getElevation();
    String getStartLocationCode();
    LocalDateTime getStartDate();
    Long getOwnerUserId();
}

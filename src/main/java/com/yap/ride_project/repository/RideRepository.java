package com.yap.ride_project.repository;

import com.yap.ride_project.dto.response.RideDetailResponseDTO;
import com.yap.ride_project.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface RideRepository extends JpaRepository<Ride, Long> {

    @Query("SELECT NEW com.yap.ride_project.dto.response.RideDetailResponseDTO(r.rideId, r.rideName, r.description, r.distance, r.elevation, r.startLocationCode, r.startDate, r.estimateTime, r.ftpLimit, r.ageLimit, r.participantLimit, r.participantMinimum, r.createdAt, r.roadbike, r.mtb, r.minivelo, r.cx, r.hybrid, r.none, r.ownerUser.userId, r.ownerUser.name) FROM Ride r WHERE r.rideId = :id")
    Optional<RideDetailResponseDTO> findDetailByRideId(long id);
}

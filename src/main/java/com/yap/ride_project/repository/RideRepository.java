package com.yap.ride_project.repository;

import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.SimpleRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    @Query("SELECT r.rideId as rideId, r.rideName as rideName, r.distance as distance, r.elevation as elevation, r.startLocationCode as startLocationCode, r.startDate as startDate, r.ownerUserId as ownerUserId from Ride r")
    List<SimpleRide> findAllSimpleRideList();

}

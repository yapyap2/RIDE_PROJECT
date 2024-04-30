package com.yap.ride_project.repository;

import com.yap.ride_project.entity.UserRideRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRideRelationRepository extends JpaRepository<UserRideRelation, Long> {

//    UserRideRelation findByUserAndRide(Long userId, Long rideId);

}

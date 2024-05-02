package com.yap.ride_project.repository;

import com.yap.ride_project.entity.UserRideRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRideRelationRepository extends JpaRepository<UserRideRelation, Long> {

    @Query("select r from UserRideRelation r where r.user.userId = :userId and r.ride.rideId = :rideId")
    UserRideRelation findByUserIdAndRideId(@Param("userId") long userId,@Param("rideId") long rideId);

    @Query("select r from UserRideRelation r where r.user.userId = :userId")
    List<UserRideRelation> findAllByUserId(@Param("userId") long userId);

    @Query("select r from UserRideRelation r where r.user.userId = :userId and r.startAt > current time ")
    List<UserRideRelation> findAllByUserIdAfterToday(@Param("userId") long userId);
}
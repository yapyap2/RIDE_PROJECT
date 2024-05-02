package com.yap.ride_project.service;

import com.yap.ride_project.entity.UserRideRelation;
import com.yap.ride_project.entity.enums.RelationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RideServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    RideService rideService;

//    @Test
//    void addRelation() {
//        rideService.addRelation(1L,1L, RelationType.LIKE);
//    }
//
//    @Test
//    void getRelatedRide() {
//        rideService.addRelation(1L, 3L, RelationType.LIKE);
//    }
}
package com.yap.ride_project.service;

import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.request.SignInRequestDTO;
import com.yap.ride_project.entity.enums.BikeType;
import com.yap.ride_project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RideServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    RideService rideService;

    @Test
    void queryRide() {

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setName("yapyap");
        signInRequestDTO.setUID("testUID");
        signInRequestDTO.setStartAt("2016-07");
        signInRequestDTO.setAge(26);
        signInRequestDTO.setFtp(4.1f);
        signInRequestDTO.setGender(0);
        signInRequestDTO.setLocationCode("1111");
        signInRequestDTO.setBikeType(List.of(BikeType.ROADBIKE, BikeType.HYBRID));

        userService.signIn(signInRequestDTO);


        CreateRideRequestDTO dto = new CreateRideRequestDTO();
        dto.setRideName("test ride");
        dto.setDistance(100D);
        dto.setDescription("this is test ride");
        dto.setUserId(1);
        dto.setBikeType(List.of(BikeType.ROADBIKE));
        dto.setStartDate("2024-05-01 10:30");
        dto.setStartLocationCode("1111");

        rideService.saveRide(dto);


    }



    static class QueryBuilder{
        private Map<String, Object> map;

        public QueryBuilder name(String name){
            map.put("name", name);
            return this;
        }

        public QueryBuilder distanceUpperLimit(Double d){
            map.put("distance_upper_limit", d);
            return this;
        }

        public QueryBuilder distanceLowerLimit(Double d){
            map.put("distance_lower_limit", d);
            return this;
        }

        public QueryBuilder startDateLeft(String d){
            map.put("distance_upper_limit", d);
            return this;
        }


        public Map<String, Object> build(){
            return map;
        }
    }
}
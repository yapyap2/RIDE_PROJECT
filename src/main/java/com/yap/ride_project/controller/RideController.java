package com.yap.ride_project.controller;

import com.yap.ride_project.dto.CreateRideRequestDTO;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PostMapping("/ride")
    public ResponseEntity<Ride> addRide(@RequestBody @Valid CreateRideRequestDTO dto){
        return ResponseEntity.ok(rideService.saveRide(dto));
    }

}

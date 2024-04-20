package com.yap.ride_project.controller;

import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.response.SimpleRideResponseDTO;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PostMapping("/ride")
    public ResponseEntity<Ride> addRide(@RequestBody @Valid CreateRideRequestDTO dto){
        return ResponseEntity.ok(rideService.saveRide(dto));
    }

    @GetMapping("/ride")
    public ResponseEntity<Ride> getSpecificRide(@RequestParam long rideId){
        return ResponseEntity.ok(rideService.getRide(rideId));
    }

    @GetMapping("/rideList")
    public ResponseEntity<List<SimpleRideResponseDTO>> getRideList(){

        return ResponseEntity.ok(rideService.getSimpleRideList());
    }

}

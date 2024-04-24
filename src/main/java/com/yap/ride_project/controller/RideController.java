package com.yap.ride_project.controller;

import com.querydsl.core.BooleanBuilder;
import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.request.RideListQuery;
import com.yap.ride_project.dto.response.SimpleRideResponseDTO;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.service.RideService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "라이딩 관련 API")
public class RideController {
    private final RideService rideService;

    @PostMapping("/ride")
    public ResponseEntity<Ride> addRide(@RequestBody @Valid CreateRideRequestDTO dto){
        return ResponseEntity.ok(rideService.saveRide(dto));
    }

    @GetMapping("/ride")
    public ResponseEntity<Ride> getSpecificRide(@RequestParam(name = "ride_id") long rideId){
        return ResponseEntity.ok(rideService.getRide(rideId));
    }

    @GetMapping("/rideList")
    public ResponseEntity<List<SimpleRideResponseDTO>> getRideList(){

        return ResponseEntity.ok(rideService.getAllRide());
    }

    @GetMapping("/rideList/query")
    public ResponseEntity<List<SimpleRideResponseDTO>> findRideList(
            @Parameter(description = "라이딩 이름 (String, like 비교)", example = "남산 라이딩")
            @RequestParam(required = false) String name,

            @Parameter(description = "라이딩 거리 최대 (Double, 이하)", example = "100")
            @RequestParam(name = "distance_upper_limit", required = false) Double distanceUpperLimit,

            @Parameter(description = "라이딩 거리 최소 (Double, 이상)", example = "100")
            @RequestParam(name = "distance_lower_limit", required = false) Double distanceLowerLimit,

            @Parameter(description = "시작 지점 지역코드 (String, equal 비교)", example = "101000")
            @RequestParam(name = "start_location_code", required = false) String startLocationCode,

            @Parameter(description = "라이딩 일자 이후 (String, 이상, yyyy-MM-dd) 값을 주지 않는 경우 당일 날짜 이후로 검색", example = "2024-04-24")
            @RequestParam(name = "start_date_left", required = false) String startDateLeft,

            @Parameter(description = "라이딩 일자 이전 (String, 이하, yyyy-MM-dd)", example = "2024-04-24")
            @RequestParam(name = "start_date_right", required = false) String startDateRight,

            @Parameter(description = "참가 가능 자전거 종류 (String BikeType 코드) , 으로 구분해서 코드 전달(공백 없어야함)",example = "R,H")
            @RequestParam(name = "bike_type", required = false) String bikeType
    ){

        RideListQuery query = new RideListQuery.Builder()
                .name(name)
                .distanceUpperLimit(distanceUpperLimit)
                .distanceLowerLimit(distanceLowerLimit)
                .startLocationCode(startLocationCode)
                .startDateLeft(startDateLeft)
                .startDateRight(startDateRight)
                .bikeType(bikeType).build();


        return ResponseEntity.ok(rideService.queryRide(query));
    }

}

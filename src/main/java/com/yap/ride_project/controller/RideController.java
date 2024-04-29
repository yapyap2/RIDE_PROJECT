package com.yap.ride_project.controller;

import com.querydsl.core.BooleanBuilder;
import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.request.RideListQuery;
import com.yap.ride_project.dto.response.SimpleRideResponseDTO;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "라이딩 추가")
    @PostMapping("/ride")
    public ResponseEntity<Ride> addRide(@RequestBody @Valid CreateRideRequestDTO dto){
        return ResponseEntity.ok(rideService.saveRide(dto));
    }

    @Operation(summary = "라이딩 id로 라이딩 조회")
    @GetMapping("/ride")
    public ResponseEntity<Ride> getSpecificRide(@RequestParam(name = "ride_id") long rideId){
        return ResponseEntity.ok(rideService.getRide(rideId));
    }

    @Operation(summary = "라이딩 검색")
    @GetMapping("/rideList")
    public ResponseEntity<List<SimpleRideResponseDTO>> getRideList(){

        return ResponseEntity.ok(rideService.getAllRide());
    }

    @GetMapping("/rideList/query")
    public ResponseEntity<List<SimpleRideResponseDTO>> findRideList(
            @Parameter(description = "라이딩 이름 (String, like 비교)")
            @RequestParam(required = false) String name,

            @Parameter(description = "라이딩 거리 최소 (Double, 이상)")
            @RequestParam(name = "distanceFrom", required = false) Double distanceLowerLimit,

            @Parameter(description = "라이딩 거리 최대 (Double, 이하)")
            @RequestParam(name = "distanceTo", required = false) Double distanceUpperLimit,

            @Parameter(description = "시작 지점 지역코드 (String, equal 비교)")
            @RequestParam(name = "startLocationCode", required = false) String startLocationCode,

            @Parameter(description ="날짜 검색 범위 지정 코드 (String, [today, half_month, full_month]")
            @RequestParam(name = "dateRange", required = false) String dateRange,

            @Parameter(description = "참가 가능 자전거 종류 (String BikeType 코드) , 으로 구분해서 코드 전달(공백 없어야함)")
            @RequestParam(name = "bikeType", required = false) String bikeType
    ){

        RideListQuery query = new RideListQuery.Builder()
                .name(name)
                .distanceUpperLimit(distanceUpperLimit)
                .distanceLowerLimit(distanceLowerLimit)
                .startLocationCode(startLocationCode)
                .dateRange(dateRange)
                .bikeType(bikeType).build();

        return ResponseEntity.ok(rideService.queryRide(query));
    }

}

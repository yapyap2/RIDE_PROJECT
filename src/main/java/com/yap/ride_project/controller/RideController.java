package com.yap.ride_project.controller;

import com.querydsl.core.BooleanBuilder;
import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.request.RideListQuery;
import com.yap.ride_project.dto.request.RideRegisterRequestDTO;
import com.yap.ride_project.dto.response.RelatedRideResponseDTO;
import com.yap.ride_project.dto.response.RideDetailResponseDTO;
import com.yap.ride_project.dto.response.RideRegisterResponseDTO;
import com.yap.ride_project.dto.response.SimpleRide;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.enums.RelationType;
import com.yap.ride_project.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<RideDetailResponseDTO> getSpecificRide(@RequestParam(name = "ride_id") long rideId){
        return ResponseEntity.ok(rideService.getRide(rideId));
    }

    @Operation(summary = "라이딩 리스트 페이징으로 가져오기")
    @GetMapping("/rideList")
    public ResponseEntity<List<SimpleRide>> getRideListPage(@RequestParam int pageNum){
        return ResponseEntity.ok(rideService.pagingRideList(pageNum));
    }

    @Operation(summary = "라이딩 검색")
    @GetMapping("/rideList/query")
    public ResponseEntity<List<SimpleRide>> findRideList(
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

    @Operation(summary = "라이딩 좋아요, 참가 등록")
    @PostMapping("/ride/register")
    public ResponseEntity<RideRegisterResponseDTO> addRelation(@RequestBody RideRegisterRequestDTO dto){
        return ResponseEntity.ok(rideService.addRelation(dto));
    }

    @Operation(summary = "좋아요 누른 라이딩 조회")

    @GetMapping("/ride/liked")
    public ResponseEntity<List<RelatedRideResponseDTO>> getLiked(@RequestParam long userId){
        return ResponseEntity.ok(rideService.getRelatedRide(userId, RelationType.LIKE));
    }


    @Operation(summary = "참가 누른 라이딩 조회")
    @GetMapping("/ride/summited")
    public ResponseEntity<List<RelatedRideResponseDTO>> getSummited(@RequestParam long userId){
        return ResponseEntity.ok(rideService.getRelatedRide(userId, RelationType.SUMMIT));
    }
}

package com.yap.ride_project.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.request.RideListQuery;
import com.yap.ride_project.dto.response.SimpleRideResponseDTO;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.SimpleRide;
import com.yap.ride_project.entity.SimpleRideQueryDSL;
import com.yap.ride_project.entity.User;
import com.yap.ride_project.exception.NotSuchRideException;
import com.yap.ride_project.exception.NotSuchUserException;
import com.yap.ride_project.exception.RideQueryParameterException;
import com.yap.ride_project.repository.RideRepository;
import com.yap.ride_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yap.ride_project.entity.QRide.ride;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RideService {
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Ride saveRide(CreateRideRequestDTO dto){

        User user = userRepository.findUserByUserId(dto.getUserId()).orElseThrow(()-> new NotSuchUserException(dto.getUserId()));
            Ride ride = makeRide(dto, user);

            rideRepository.save(ride);
            return ride;
    }

    public Ride getRide(long rideId){
        return rideRepository.findById(rideId).orElseThrow(() -> new NotSuchRideException(rideId));
    }
    public List<SimpleRideResponseDTO> getAllRide(){

        List<SimpleRide> list = rideRepository.findAllSimpleRideList();

        return makeResponseDtoList(list);
    }

    public List<SimpleRideResponseDTO> queryRide(RideListQuery query){

        BooleanBuilder builder = query.getBuilder();


        List<SimpleRideQueryDSL> result = jpaQueryFactory
                .select(Projections.fields(SimpleRideQueryDSL.class, ride.rideId, ride.rideName, ride.distance, ride.elevation, ride.startLocationCode, ride.startDate, ride.ownerUserId))
                .from(ride)
                .where(builder)
                .fetch();

        return makeResponseDtoList(result);
    }


    private Ride makeRide(CreateRideRequestDTO dto, User user){
        Ride ride = Ride.builder().
                ownerUser(user).
                rideName(dto.getRideName()).
                distance(dto.getDistance()).
                description(dto.getDescription()).
                startDate(LocalDateTime.parse(dto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).
                startLocationCode(dto.getStartLocationCode()).
                elevation(dto.getElevation()).
                bikeType(dto.getBikeType()).
                estimateTime(dto.getEstimateTime()).
                ftpLimit(dto.getFtpLimit()).
                ageLimit(dto.getAgeLimit()).
                participantLimit(dto.getParticipantLimit()).
                participantMinimum(dto.getParticipantMinimum()).build();

        return ride;
    }

    private List<SimpleRideResponseDTO> makeResponseDtoList(List<? extends SimpleRide> list){
        List<SimpleRideResponseDTO> dtoList = new ArrayList<>();
        list.forEach(l ->
                dtoList.add(new SimpleRideResponseDTO(l))
        );
        return dtoList;
    }
}

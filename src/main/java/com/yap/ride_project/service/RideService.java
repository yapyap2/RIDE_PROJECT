package com.yap.ride_project.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.request.RideListQuery;
import com.yap.ride_project.dto.response.SimpleRide;
import com.yap.ride_project.entity.*;
import com.yap.ride_project.exception.NotSuchRideException;
import com.yap.ride_project.exception.NotSuchUserException;
import com.yap.ride_project.repository.RideRepository;
import com.yap.ride_project.repository.UserRepository;
import com.yap.ride_project.repository.UserRideRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yap.ride_project.entity.QRide.ride;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final UserRideRelationRepository userRideRelationRepository;

    public Ride saveRide(CreateRideRequestDTO dto){

        User user = userRepository.findUserByUserId(dto.getUserId()).orElseThrow(()-> new NotSuchUserException(dto.getUserId()));
            Ride ride = makeRide(dto, user);

            rideRepository.save(ride);
            return ride;
    }

    public Ride getRide(long rideId){
        return rideRepository.findById(rideId).orElseThrow(() -> new NotSuchRideException(rideId));
    }

    public List<SimpleRide> queryRide(RideListQuery query){

        BooleanBuilder builder = query.getBuilder();

        List<SimpleRide> result = jpaQueryFactory
                .select(Projections.fields(SimpleRide.class, ride.rideId, ride.rideName, ride.distance, ride.elevation, ride.startLocationCode, ride.startDate, ride.ownerUser.userId.as("ownerUserId"), ride.ownerUser.name.as("ownerUserName")))
                .from(ride)
                .where(builder)
                .fetch();

        return result;
    }

//    public void addRelation(Long userId, Long rideId, RelationType type){
//        User user = userRepository.findById(1L).orElseThrow(() -> new NotSuchUserException(userId));
//        Ride ride = rideRepository.findById(1L).orElseThrow(() -> new NotSuchRideException(rideId));
//
//
//        UserRideRelation relation = userRideRelationRepository.findByUserAndRide(1L, 2L);
//
//        if(relation == null) {
//            relation = new UserRideRelation(user,ride);
//        }
//        relation.updateRelation(type);
//
//
//    }


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
}

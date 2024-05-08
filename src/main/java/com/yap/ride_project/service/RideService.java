package com.yap.ride_project.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.request.RideListQuery;
import com.yap.ride_project.dto.request.RideRegisterRequestDTO;
import com.yap.ride_project.dto.response.*;
import com.yap.ride_project.entity.*;
import com.yap.ride_project.entity.enums.RelationType;
import com.yap.ride_project.exception.NotSuchRideException;
import com.yap.ride_project.exception.NotSuchUserException;
import com.yap.ride_project.repository.RideRepository;
import com.yap.ride_project.repository.UserRepository;
import com.yap.ride_project.repository.UserRideRelationRepository;
import io.micrometer.observation.Observation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yap.ride_project.entity.QRide.ride;
import static com.yap.ride_project.entity.QUserRideRelation.userRideRelation;

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

    public RideDetailResponseDTO getRide(long rideId){
        return rideRepository.findDetailByRideId(rideId).orElseThrow(() -> new NotSuchRideException(rideId));
    }

    public List<SimpleRide> pagingRideList(int pageNum){
        List<SimpleRide> result = jpaQueryFactory
                .select(Projections.fields(SimpleRide.class, ride.rideId, ride.rideName, ride.distance, ride.elevation, ride.startLocationCode, ride.startDate, ride.ownerUser.userId.as("ownerUserId"), ride.ownerUser.name.as("ownerUserName")))
                .from(ride)
                .where(ride.startDate.after(LocalDateTime.now()))
                .offset((pageNum - 1) * 10)
                .limit(10)
                .fetch();
        return result;
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

    public RideRegisterResponseDTO addRelation(RideRegisterRequestDTO dto){
        long userId = dto.getUserId();
        long rideId = dto.getRideId();

        User user = userRepository.findById(userId).orElseThrow(() -> new NotSuchUserException(userId));
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new NotSuchRideException(rideId));


        UserRideRelation relation = userRideRelationRepository.findByUserIdAndRideId(userId, rideId);

        if(relation == null) {
            relation = new UserRideRelation(user,ride);
        }
        relation.updateRelation(dto.getRelationType());

        userRideRelationRepository.save(relation);

        return new RideRegisterResponseDTO(userId, rideId, ride.getRideName(), dto.getRelationType());
    }

    public List<RelatedRideResponseDTO> getRelatedRide(long userId, RelationType type){ //조회 범위 일자 추가 필요

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(userRideRelation.user.userId.eq(userId))
                .and(userRideRelation.createAt.after(LocalDateTime.now().minusMonths(1)));

        if(type == RelationType.LIKE) builder.and(userRideRelation.liked.isTrue());
        if(type == RelationType.SUMMIT) builder.and(userRideRelation.summited.isTrue());
        
        
        List<RelatedRideResponseDTO> list =  jpaQueryFactory.select(new QRelatedRideResponseDTO(ride.rideId, ride.rideName, ride.distance, ride.elevation, ride.startLocationCode, ride.startDate, ride.ownerUser.userId, ride.ownerUser.name, userRideRelation.createAt, userRideRelation.summited,userRideRelation.liked))
                .from(userRideRelation)
                .where(builder).fetch();
        return list;
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
}

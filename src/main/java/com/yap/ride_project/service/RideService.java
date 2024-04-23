package com.yap.ride_project.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yap.ride_project.dto.request.CreateRideRequestDTO;
import com.yap.ride_project.dto.response.SimpleRideResponseDTO;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.SimpleRide;
import com.yap.ride_project.entity.SimpleRideQueryDSL;
import com.yap.ride_project.entity.User;
import com.yap.ride_project.exception.NotSuchRideException;
import com.yap.ride_project.exception.NotSuchUserException;
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

    public List<SimpleRideResponseDTO> queryRide(Map<String, Object> query){

        if(!query.containsKey("startDateLeft")){
            query.put("startDateLeft", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        BooleanBuilder builder = new BooleanBuilder();
        for(String key : query.keySet()){
            if(key.equals("name")) builder.and(ride.rideName.contains((String) query.get(key)));
            if(key.equals("distance_upper_limit")) builder.and(ride.distance.loe(Double.parseDouble((String) query.get("distance_upper_limit"))));
            if(key.equals("distance_lower_limit")) builder.and(ride.distance.goe(Double.parseDouble((String) query.get("distance_lower_limit"))));
            if(key.equals("start_location_code")) builder.and(ride.startLocationCode.eq((String) query.get("start_location_code")));
            if(key.equals("start_date_left")) builder.and(ride.startDate.after(LocalDateTime.parse((String) query.get("start_date_left")+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            if(key.equals("start_date_right")) builder.and(ride.startDate.before(LocalDateTime.parse((String) query.get("start_date_right")+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            if(key.equals("bike_type")){
                for(String type : (List<String>) query.get(key)){
                    if(type.equals("R")) builder.and(ride.roadbike.isTrue());
                    if(type.equals("M")) builder.and(ride.mtb.isTrue());
                    if(type.equals("H")) builder.and(ride.hybrid.isTrue());
                    if(type.equals("V")) builder.and(ride.minivelo.isTrue());
                    if(type.equals("N")) builder.and(ride.none.isTrue());
                    if(type.equals("C")) builder.and(ride.cx.isTrue());
                }
            }
        }

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

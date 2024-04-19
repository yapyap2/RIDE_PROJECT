package com.yap.ride_project.service;

import com.yap.ride_project.dto.CreateRideRequestDTO;
import com.yap.ride_project.entity.Ride;
import com.yap.ride_project.entity.User;
import com.yap.ride_project.exception.NotSuchUserException;
import com.yap.ride_project.repository.RideRepository;
import com.yap.ride_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RideService {
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    public Ride saveRide(CreateRideRequestDTO dto){

        User user = userRepository.findUserByUserId(dto.getUserId()).orElseThrow(()-> new NotSuchUserException(dto.getUserId()));
            Ride ride = makeRide(dto, user);

            rideRepository.save(ride);
            return ride;
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

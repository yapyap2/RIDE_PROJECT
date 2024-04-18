package com.yap.ride_project.service;

import com.yap.ride_project.entity.User;
import com.yap.ride_project.dto.SignInRequestDTO;
import com.yap.ride_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    @Transactional
    public User signIn(SignInRequestDTO dto){

        User user = User.builder()
                .UID(dto.getUID())
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .ftp(dto.getFtp())
                .location_code(dto.getLocationCode())
                .startAt(LocalDate.parse(dto.getStartAt() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .bikeType(dto.getBikeType())
                .build();

        userRepository.save(user);
        return user;
    }

}

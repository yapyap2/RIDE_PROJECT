package com.yap.ride_project.service;

import com.yap.ride_project.entity.User;
import com.yap.ride_project.dto.SignInRequestDTO;
import com.yap.ride_project.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class UserService {

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
                .startAt(dto.getStartAt())
                .bikeType(dto.getBikeType())
                .build();

        userRepository.save(user);
        return user;
    }

    public User getUser(Long id){
        return userRepository.findUserByUserId(id).orElseThrow(() -> new EntityNotFoundException("Entity not found with user_id: " + id));
    }

}

package com.yap.ride_project.service;

import com.yap.ride_project.User;
import com.yap.ride_project.dto.SignInRequestDTO;
import com.yap.ride_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .sex(dto.getSex()).
                ftp(dto.getFtp()).build();


        userRepository.save(user);
        return user;
    }

}

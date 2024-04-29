package com.yap.ride_project.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yap.ride_project.dto.request.UpdateUserRequestDTO;
import com.yap.ride_project.entity.User;
import com.yap.ride_project.dto.request.SignInRequestDTO;
import com.yap.ride_project.exception.NotSuchUserException;
import com.yap.ride_project.exception.UIDMismatchException;
import com.yap.ride_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yap.ride_project.entity.QUser.user;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    public User signIn(SignInRequestDTO dto){

        User user = User.builder()
                .UID(dto.getUID())
                .name(dto.getName())
                .description(dto.getDescription())
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
        return userRepository.findUserByUserId(id).orElseThrow(() -> new NotSuchUserException(id));
    }

    @Transactional
    public User updateUser(UpdateUserRequestDTO dto){

        User user = userRepository.findUserByUserId(dto.getId()).orElseThrow(() -> new NotSuchUserException(dto.getId()));

        if(!user.getUID().equals(dto.getUID())) throw new UIDMismatchException(dto.getUID());

        user.update(dto);

        return user;
    }

}

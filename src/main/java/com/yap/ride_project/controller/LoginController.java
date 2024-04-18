package com.yap.ride_project.controller;

import com.yap.ride_project.entity.User;
import com.yap.ride_project.dto.SignInRequestDTO;
import com.yap.ride_project.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<User> signIn(@RequestBody @Valid SignInRequestDTO requestDTO){
        User user = loginService.signIn(requestDTO);

        return ResponseEntity.ok(user);
    }


}

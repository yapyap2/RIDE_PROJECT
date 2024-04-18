package com.yap.ride_project.controller;

import com.yap.ride_project.entity.User;
import com.yap.ride_project.dto.SignInRequestDTO;
import com.yap.ride_project.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<User> signIn(@RequestBody SignInRequestDTO requestDTO){
        User user = loginService.signIn(requestDTO);

        return ResponseEntity.ok(user);
    }


}

package com.yap.ride_project.controller;

import com.yap.ride_project.entity.User;
import com.yap.ride_project.dto.SignInRequestDTO;
import com.yap.ride_project.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Long>> signIn(@RequestBody @Valid SignInRequestDTO requestDTO){
        User user = userService.signIn(requestDTO);
        Map<String, Long> map = new HashMap<>();
        map.put("user_id", user.getUserId());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam Long userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }


}

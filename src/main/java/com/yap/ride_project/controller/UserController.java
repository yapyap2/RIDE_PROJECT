package com.yap.ride_project.controller;

import com.yap.ride_project.entity.User;
import com.yap.ride_project.dto.request.SignInRequestDTO;
import com.yap.ride_project.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "유저 정보 관련 API")
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

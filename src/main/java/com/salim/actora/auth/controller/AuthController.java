package com.salim.actora.auth.controller;

import com.salim.actora.auth.dto.LoginDto;
import com.salim.actora.auth.entity.User;
import com.salim.actora.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/auth/create")
    public ResponseEntity<?> createUser(@RequestBody LoginDto loginDto){

       User user = authService.createUser(loginDto);

       return ResponseEntity.ok(user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto){
       String accessToken = authService.createJwtAccessToken(loginDto);

       return ResponseEntity.ok(accessToken);
    }
}

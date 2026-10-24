package com.salim.actora.auth.controller;

import com.salim.actora.auth.dto.LoginDto;
import com.salim.actora.auth.dto.LoginResponseDto;
import com.salim.actora.auth.dto.RefreshTokenDto;
import com.salim.actora.auth.entity.User;
import com.salim.actora.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody LoginDto loginDto){

       User user = authService.createUser(loginDto);

       return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto){
       LoginResponseDto loginResponseDto = authService.createJwtAccessToken(loginDto);

       return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDto tokenDto){

        System.out.println("entered refresh token controller method "+tokenDto.getToken());

        log.debug("entered refresh token controller method {}",tokenDto.getToken());

        LoginResponseDto loginResponseDto = authService.renewRefreshAndAccessToken(tokenDto.getToken());

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenDto tokenDto){

         authService.logout(tokenDto.getToken());

        return ResponseEntity.noContent().build();
    }


}

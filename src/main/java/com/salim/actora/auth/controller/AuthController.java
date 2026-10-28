package com.salim.actora.auth.controller;

import com.salim.actora.auth.dto.CreateUserDto;
import com.salim.actora.auth.dto.LoginDto;
import com.salim.actora.auth.dto.LoginResponseDto;
import com.salim.actora.auth.dto.RefreshTokenDto;
import com.salim.actora.auth.entity.User;
import com.salim.actora.auth.services.AuthService;
import com.salim.actora.util.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDto dto){
        System.out.println("entered create user controller method");

       User user = authService.createUser(dto);

        System.out.println("created user in controller method user: "+user.getUsername());

       return ResponseBuilder.build(user,null, HttpStatus.OK, LocalDateTime.now());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDto loginDto){
       LoginResponseDto loginResponseDto = authService.createJwtAccessToken(loginDto);

       return ResponseBuilder.build(loginResponseDto,null,HttpStatus.OK,LocalDateTime.now());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenDto tokenDto){

        LoginResponseDto loginResponseDto = authService.renewRefreshAndAccessToken(tokenDto.getToken());

        return ResponseBuilder.build(loginResponseDto,null,HttpStatus.OK,LocalDateTime.now());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid RefreshTokenDto tokenDto){

         authService.logout(tokenDto.getToken());

        return ResponseEntity.noContent().build();
    }


}

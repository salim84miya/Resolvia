package com.salim.actora.auth.services;

import com.salim.actora.auth.config.JwtConfig;
import com.salim.actora.auth.dto.LoginDto;
import com.salim.actora.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtConfig jwt;
    private final AuthenticationManager authenticationManager;


    public String createJwtAccessToken(LoginDto loginDto){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword())
        );


        User user = (User) authentication.getPrincipal();

        String token = jwt.createAccessToken(user);

        return token;

    }


}

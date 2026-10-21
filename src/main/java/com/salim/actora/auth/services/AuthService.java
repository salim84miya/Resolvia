package com.salim.actora.auth.services;

import com.salim.actora.auth.config.JwtConfig;
import com.salim.actora.auth.dto.LoginDto;
import com.salim.actora.auth.entity.User;
import com.salim.actora.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtConfig jwt;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public User createUser(LoginDto dto){

        Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());

        if(existingUser.isPresent()){
            throw new RuntimeException("User Already exists with username");
        }

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);

    }
}

package com.salim.actora.auth.services;

import com.salim.actora.auth.config.JwtService;
import com.salim.actora.auth.dto.CreateUserDto;
import com.salim.actora.auth.dto.LoginDto;
import com.salim.actora.auth.dto.LoginResponseDto;
import com.salim.actora.auth.entity.RefreshToken;
import com.salim.actora.auth.entity.Role;
import com.salim.actora.auth.entity.User;
import com.salim.actora.auth.repository.RefreshTokenRepository;
import com.salim.actora.auth.repository.RoleRepository;
import com.salim.actora.auth.repository.UserRepository;
import com.salim.actora.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtService jwt;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository repository;
    private final RoleRepository roleRepository;


    public LoginResponseDto createJwtAccessToken(LoginDto loginDto){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();


        if(user==null){
            throw new RuntimeException("No user found");
        }

        String jti = UUID.randomUUID().toString();

        RefreshToken refreshTokenObj = RefreshToken.builder()
                .jti(jti)
                .user(user)
                .isRevoked(false)
                .expiredAt(LocalDateTime.now().plusDays(7))
                .build();

        repository.save(refreshTokenObj);

        String accessToken = jwt.createAccessToken(user);
        String refreshToken = jwt.createRefreshToken(user,jti);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userName(user.getUsername())
                .build();

    }

    public LoginResponseDto renewRefreshAndAccessToken(String token){

        System.out.println("Entered renew refresh access token "+token);

        log.debug("Entered renew refresh access token {}",token);

        if(!jwt.isRefreshToken(token)){
            throw new RuntimeException("Not an refresh token");
        }

        System.out.println("is refresh token"+token);

        String oldJti = jwt.getJtiFromToken(token).toString();
        UUID userId = jwt.getUserIdFromToken(token);

        System.out.println("fetched old jti "+oldJti);
        System.out.println("fetched userId "+userId);

        RefreshToken storedRefreshToken = repository.findByJti(oldJti).orElseThrow(
                ()->new BadCredentialsException("No jti found"));

        System.out.println("stored refresh token "+storedRefreshToken.getJti());

        if(storedRefreshToken.getIsRevoked()){
            System.out.println("Token already revoked "+storedRefreshToken.getIsRevoked());
           throw new RuntimeException("Token is already revoked");
        }


        if(storedRefreshToken.getExpiredAt().isBefore(LocalDateTime.now())){

            System.out.println("token already expired");

            throw new RuntimeException("Token already Expired");
        }

        if(!storedRefreshToken.getUser().getId().equals(userId)){

            System.out.println("token doesn't belong to user");

            throw new RuntimeException("token doesn't belong to user");
        }

        String newJti = UUID.randomUUID().toString();

        System.out.println("new jti generated "+newJti);

        storedRefreshToken.setIsRevoked(true);

        System.out.println("token revoked "+storedRefreshToken.getIsRevoked());

        storedRefreshToken.setReplacedBy(newJti);

        System.out.println("replaced by new jti "+storedRefreshToken.getReplacedBy());

        repository.save(storedRefreshToken);

        System.out.println("changes to old refresh token saved");

        User user = storedRefreshToken.getUser();

        System.out.println("User fetched from token "+user.getUsername());

        RefreshToken refreshTokenObj = RefreshToken.builder()
                .jti(newJti)
                .user(user)
                .isRevoked(false)
                .expiredAt(LocalDateTime.now().plusDays(7))
                .build();

        System.out.println("new refresh token object created "+refreshTokenObj.getJti());

        repository.save(refreshTokenObj);

        System.out.println("new refresh token saved");

        String accessToken = jwt.createAccessToken(user);

        System.out.println("new accessToken generated "+accessToken);

        String refreshToken = jwt.createRefreshToken(user,newJti);

        System.out.println("new refreshToken generated "+refreshToken);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userName(user.getUsername())
                .build();



    }

    public void logout(String refreshToken){

        if(!jwt.isRefreshToken(refreshToken)){
            throw new RuntimeException("Not an refresh token");
        }

        String jti = jwt.getJtiFromToken(refreshToken).toString();

        System.out.println("extracted jti fro logout "+jti);

        repository.findByJti(jti)
                .ifPresent(rt->{
                    rt.setIsRevoked(true);
                    repository.save(rt);
                });

        System.out.println("user revoked and logout successfully");


    }

    public User createUser(CreateUserDto dto){

        Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());


        System.out.println("entered create user method service is Present"+existingUser.isPresent());

        if(existingUser.isPresent()){
            throw new RuntimeException("User Already exists with username");
        }

        User user = new User();

        Role role = null;

        if(dto.getRole()!=null && !dto.getRole().isBlank()){
            role = roleRepository.findByName("ROLE_"+dto.getRole())
                    .orElseThrow(()->new RuntimeException("No role found"));
        }else{
            role = roleRepository.findByName("ROLE_"+AppConstants.USER_ROLE)
                    .orElseThrow(()->new RuntimeException("No role found"));
        }

        user.getRoles().add(role);

        System.out.println("User role set");


        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        System.out.println("Saving user");

        return userRepository.save(user);

    }
}

package com.salim.actora.auth.config;

import com.salim.actora.auth.entity.User;
import com.salim.actora.auth.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {


    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String requestTokenHeader = request.getHeader("Authorization");

            if(requestTokenHeader==null || requestTokenHeader.startsWith("Bearer")){

                filterChain.doFilter(request,response);
                return;
            }

            String token = requestTokenHeader.split("Bearer")[1];

            String username = jwtConfig.findUsernameByToken(token);

            if(username!=null || SecurityContextHolder.getContext().getAuthentication()==null){

                User user = userRepository.findByUsername(username)
                        .orElseThrow(()->new IllegalArgumentException("No user found"));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,null,user.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request,response);
            }
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }


    }
}

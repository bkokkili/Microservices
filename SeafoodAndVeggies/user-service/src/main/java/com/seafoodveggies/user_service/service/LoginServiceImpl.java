package com.seafoodveggies.user_service.service;

import com.seafoodveggies.user_service.dto.LoginDto;
import com.seafoodveggies.user_service.dto.LoginResponseDto;
import com.seafoodveggies.user_service.dto.RefreshTokenResponse;
import com.seafoodveggies.user_service.entity.User;
import com.seafoodveggies.user_service.exception.InvalidCredentialsException;
import com.seafoodveggies.user_service.exception.InvalidTokenException;
import com.seafoodveggies.user_service.exception.TokenExpiredException;
import com.seafoodveggies.user_service.exception.TokenGenerationException;
import com.seafoodveggies.user_service.repo.UserRepository;
import com.seafoodveggies.user_service.security.CustomUserDetails;
import com.seafoodveggies.user_service.security.JwtService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements  LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResponseDto loginUser(LoginDto loginDto) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( loginDto.getUserName(), loginDto.getPassword())
            );

            if (authentication.isAuthenticated()){
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                User user = userDetails.getUser();
                String accessToken =  jwtService.generateToken(user);
                String refreshToken = jwtService.refreshToken(user);
                return new LoginResponseDto(user, accessToken, refreshToken);
            }
        }catch (BadCredentialsException e){
            throw new InvalidCredentialsException("Invalid Password");
        }catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("Invalid User");
        }catch (Exception e){
            LOG.error("Error while login", e);
        }
        return null;

    }

    @Override
    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        String newAccessToken = null;
        try {

            if (!jwtService.isRefreshToken(refreshToken)){
                throw  new InvalidTokenException("Provided token is not a valid refresh token");
            }

            String userName = jwtService.extractUserName(refreshToken);

            User user = userRepository.findByUserName(userName);

            newAccessToken = jwtService.generateToken(user);

            return new RefreshTokenResponse(newAccessToken);

        }catch (TokenExpiredException e){
           throw new TokenGenerationException("Refresh token expired");
        }catch (InvalidTokenException e){
            throw new InvalidTokenException("Invalid refresh token");
        } catch (Exception e){
            LOG.error("Error while fetching refresh token", e);
        }

        return null;
    }
}

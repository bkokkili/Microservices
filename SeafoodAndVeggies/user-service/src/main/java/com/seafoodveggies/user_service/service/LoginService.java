package com.seafoodveggies.user_service.service;

import com.seafoodveggies.user_service.dto.LoginDto;
import com.seafoodveggies.user_service.dto.LoginResponseDto;
import com.seafoodveggies.user_service.dto.RefreshTokenResponse;
//import com.seafoodveggies.user_service.dto.RefreshTokenResponse;

public interface LoginService {
    LoginResponseDto loginUser(LoginDto loginDto);
    RefreshTokenResponse refreshAccessToken(String refreshToken);
}

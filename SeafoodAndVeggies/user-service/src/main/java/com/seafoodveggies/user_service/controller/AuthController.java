package com.seafoodveggies.user_service.controller;

import com.seafoodveggies.user_service.dto.LoginDto;
import com.seafoodveggies.user_service.dto.LoginResponseDto;
import com.seafoodveggies.user_service.dto.RefreshTokenResponse;
import com.seafoodveggies.user_service.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto loginDto){

        return loginService.loginUser(loginDto);
    }

    public RefreshTokenResponse refreshToken(@PathVariable String refreshToken){
        return loginService.refreshAccessToken(refreshToken);
    }

}

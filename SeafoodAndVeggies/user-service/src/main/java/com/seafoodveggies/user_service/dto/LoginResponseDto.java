package com.seafoodveggies.user_service.dto;

import com.seafoodveggies.user_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private User user;

    private String accessToken;

    private String refreshToken;

}

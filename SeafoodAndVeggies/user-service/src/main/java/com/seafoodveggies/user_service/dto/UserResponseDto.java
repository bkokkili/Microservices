package com.seafoodveggies.user_service.dto;

import com.seafoodveggies.user_service.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String mobile;
    private List<Address> addresses = new ArrayList<>();
}

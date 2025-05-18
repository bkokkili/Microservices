package com.seafoodveggies.user_service.service;

import com.seafoodveggies.user_service.dto.UserResponseDto;
import com.seafoodveggies.user_service.entity.User;

public interface UserService {
    public User saveUser(User user);
    User findByUserName(String userName);
    void deleteUser(Long userId);
    User updateUser(Long id, User updatedUser);
    UserResponseDto getUserById(Long id);
}

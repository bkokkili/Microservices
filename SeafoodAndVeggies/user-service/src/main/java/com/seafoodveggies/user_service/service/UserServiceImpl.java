package com.seafoodveggies.user_service.service;

import com.seafoodveggies.user_service.client.AddressFeignClient;
import com.seafoodveggies.user_service.model.Address;
import com.seafoodveggies.user_service.dto.UserResponseDto;
import com.seafoodveggies.user_service.entity.User;
import com.seafoodveggies.user_service.exception.ResourceNotFoundException;
import com.seafoodveggies.user_service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private AddressFeignClient addressFeignClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (updatedUser.getUserName() != null && !updatedUser.getUserName().isEmpty()) {
                existingUser.setUserName(updatedUser.getUserName());
            }

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
            }

            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getMobile() != null && !updatedUser.getMobile().isEmpty()) {
                existingUser.setMobile(updatedUser.getMobile());
            }

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        UserResponseDto userWithAddresses = null;
        List<Address> addresses = addressFeignClient.getAddressByUserId(id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            User userData = user.get();
             userWithAddresses = new UserResponseDto(userData.getId(), userData.getUserName(),
                      userData.getPassword(), userData.getEmail(), userData.getMobile(), addresses);
        }
        return userWithAddresses ;
    }
}

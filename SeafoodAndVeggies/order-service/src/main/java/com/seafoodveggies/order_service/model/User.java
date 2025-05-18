package com.seafoodveggies.order_service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String mobile;
    private List<Address> addresses = new ArrayList<>();
}

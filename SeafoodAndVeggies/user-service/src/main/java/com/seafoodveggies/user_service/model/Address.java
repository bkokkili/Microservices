package com.seafoodveggies.user_service.model;

public record Address(
        Long id,
        String fullName,
        String phoneNumber,
        String flatOrHouseNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        Boolean isDefault
) { }
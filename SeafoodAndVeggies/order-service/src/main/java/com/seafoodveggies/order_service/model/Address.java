package com.seafoodveggies.order_service.model;



public record Address(
        int id,
        String fullName,
        String phoneNumber,
        String flatOrHouseNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country
) {}

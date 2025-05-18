package com.seafoodveggies.address_service.service;

import com.seafoodveggies.address_service.entity.Address;

import java.util.List;

public interface AddressService {
    Address save(Address address);
    Address update(Long addressId, Address address);
    void delete(Long addressId);
    List<Address> getByUserId(Long userId);
}

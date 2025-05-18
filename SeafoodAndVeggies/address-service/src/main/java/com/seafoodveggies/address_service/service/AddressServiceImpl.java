package com.seafoodveggies.address_service.service;

import com.seafoodveggies.address_service.entity.Address;
import com.seafoodveggies.address_service.repo.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService{
    private static  final Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Long addressId, Address updated) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        address.setFullName(updated.getFullName());
        address.setPhoneNumber(updated.getPhoneNumber());
        address.setFlatOrHouseNumber(updated.getFlatOrHouseNumber());
        address.setAddressLine1(updated.getAddressLine1());
        address.setAddressLine2(updated.getAddressLine2());
        address.setCity(updated.getCity());
        address.setState(updated.getState());
        address.setPostalCode(updated.getPostalCode());
        address.setCountry(updated.getCountry());
        address.setIsDefault(updated.getIsDefault());
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<Address> getByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }
}


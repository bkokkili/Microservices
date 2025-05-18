package com.seafoodveggies.address_service.controller;

import com.seafoodveggies.address_service.entity.Address;
import com.seafoodveggies.address_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<Address> add(@RequestBody Address address) {
        return ResponseEntity.ok(addressService.save(address));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getByUserId(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody Address address) {
        return ResponseEntity.ok(addressService.update(id, address));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
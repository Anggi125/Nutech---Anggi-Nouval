package com.example.simsppob.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simsppob.jwt.JwtUtil;
import com.example.simsppob.repositories.ServiceRepository;
import com.example.simsppob.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.example.simsppob.models.Service> getAllServices() {
        String email = jwtUtil.getEmailFromCurrentRequest();
        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        return serviceRepository.findAll();
    }

    public Optional<com.example.simsppob.models.Service> getServiceByCode(String serviceCode) {
        return serviceRepository.findByServiceCode(serviceCode);
    }
}
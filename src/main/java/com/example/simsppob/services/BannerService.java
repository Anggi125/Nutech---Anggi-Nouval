package com.example.simsppob.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simsppob.models.Banner;
import com.example.simsppob.repositories.BannerRepository;

import java.util.List;

@Service
public class BannerService {
    
    @Autowired
    private BannerRepository bannerRepository;
    
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }
}
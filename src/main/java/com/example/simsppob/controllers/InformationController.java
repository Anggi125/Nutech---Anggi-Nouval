package com.example.simsppob.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simsppob.dto.ApiResponse;
import com.example.simsppob.models.Banner;
import com.example.simsppob.models.Service;
import com.example.simsppob.services.BannerService;
import com.example.simsppob.services.ServiceService;

@RestController
public class InformationController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ServiceService service;

    @GetMapping("/banner")
    public ResponseEntity<?> getBanner(){
        List<Banner> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(new ApiResponse<>(0, "sukses", banners));
    }

    @GetMapping("/services")
    public ResponseEntity<?> getServices() {
        List<Service> services = service.getAllServices();
        return ResponseEntity.ok(new ApiResponse<>(0, "Sukses", services));
    }
}

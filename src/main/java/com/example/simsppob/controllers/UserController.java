package com.example.simsppob.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.simsppob.dto.ApiResponse;
import com.example.simsppob.dto.LoginRequest;
import com.example.simsppob.dto.RegistrationRequest;
import com.example.simsppob.dto.UpdateProfileRequest;
import com.example.simsppob.services.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>(0, "BERHASIL REGIS", null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
         return ResponseEntity.ok(new ApiResponse<>(0, "Login Sukses", authService.login(request)));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(){
        return ResponseEntity.ok(new ApiResponse<>(0, "sukses", authService.profile()));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(0, "sukses", authService.editProfile(request)));
    }

    @PutMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfileImage(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(new ApiResponse<>(0, "sukses", authService.editProfileImage(file)));
    }
}

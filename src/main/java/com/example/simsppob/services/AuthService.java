package com.example.simsppob.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.simsppob.dto.LoginRequest;
import com.example.simsppob.dto.ProfileResponse;
import com.example.simsppob.dto.RegistrationRequest;
import com.example.simsppob.dto.UpdateProfileRequest;

public interface AuthService {
    void register(RegistrationRequest request);
    String login(LoginRequest request);
    ProfileResponse profile();
    ProfileResponse editProfile(UpdateProfileRequest request);
    ProfileResponse editProfileImage(MultipartFile file);
}

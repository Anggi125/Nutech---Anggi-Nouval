package com.example.simsppob.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String profileImage;
}
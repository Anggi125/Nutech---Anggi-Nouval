package com.example.simsppob.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {
    @Email(message = "Parameter email tidak sesuai format")
    @NotBlank(message = "Parameter email harus di isi")
    private String email;

    @NotBlank(message = "Parameter first_name harus di isi")
    @JsonProperty("first_name") 
    private String firstName;

    @NotBlank(message = "Parameter last_name harus di isi")
    @JsonProperty("last_name") 
    private String lastName;

    @Size(min = 8, message = "Password minimal 8 karakter")
    @NotBlank(message = "Parameter password harus di isi")
    private String password;
}
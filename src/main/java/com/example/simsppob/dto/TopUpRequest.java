package com.example.simsppob.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TopUpRequest {
    @Min(value = 1, message = "Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
    @JsonProperty("top_up_amount") 
    private Long topUpAmount;
}
package com.example.simsppob.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Integer status;
    private String message;
    private T data;
    
    public ApiResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
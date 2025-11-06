package com.example.simsppob.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionResponse {
    private String invoiceNumber;
    private String serviceCode;
    private String serviceName;
    private String transactionType;
    private Long totalAmount;
    private LocalDateTime createdOn;
}
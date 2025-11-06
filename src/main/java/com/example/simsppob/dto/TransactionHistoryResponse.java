package com.example.simsppob.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class TransactionHistoryResponse {
    private Integer offset;
    private Integer limit;
    private List<TransactionRecord> records;
    
    @Data
    public static class TransactionRecord {
        private String invoiceNumber;
        private String transactionType;
        private String description;
        private Long totalAmount;
        private LocalDateTime createdOn;
    }
}
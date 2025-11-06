package com.example.simsppob.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "invoice_number")
    private String invoiceNumber;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "transaction_type") // TOPUP or PAYMENT
    private String transactionType;
    
    @Column(name = "service_code")
    private String serviceCode;
    
    @Column(name = "service_name")
    private String serviceName;
    
    @Column(name = "total_amount")
    private Long totalAmount;
    
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    
    private String description;
}
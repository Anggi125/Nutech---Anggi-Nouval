package com.example.simsppob.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "service_code", unique = true)
    private String serviceCode;
    
    @Column(name = "service_name")
    private String serviceName;
    
    @Column(name = "service_icon")
    private String serviceIcon;
    
    @Column(name = "service_tariff")
    private Long serviceTariff;
}
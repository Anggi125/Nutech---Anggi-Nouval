package com.example.simsppob.services;

import com.example.simsppob.dto.TopUpRequest;
import com.example.simsppob.dto.TransactionHistoryResponse;
import com.example.simsppob.dto.TransactionResponse;

public interface TransactionService {
    Long getBalance();
    Long topUp(TopUpRequest amount);
    TransactionResponse createTransaction( String serviceCode);
    TransactionHistoryResponse getTransactionHistory( Integer offset, Integer limit);
}

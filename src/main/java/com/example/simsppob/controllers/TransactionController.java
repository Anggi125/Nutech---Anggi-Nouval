package com.example.simsppob.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.simsppob.dto.ApiResponse;
import com.example.simsppob.dto.TransactionHistoryResponse;
import com.example.simsppob.dto.TransactionRequest;
import com.example.simsppob.dto.TransactionResponse;
import com.example.simsppob.services.TransactionService;
import com.example.simsppob.dto.TopUpRequest;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {
        return ResponseEntity.ok(new ApiResponse<>(0, "Sukses", transactionService.getBalance()));
    }

    @PostMapping("/topup")
    public ResponseEntity<?> topUp(@RequestBody TopUpRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(0, "Sukses", transactionService.topUp(request)));
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@RequestBody TransactionRequest request) {
        try {
            TransactionResponse transaction = transactionService.createTransaction(request.getServiceCode());
            return ResponseEntity.ok(new ApiResponse<>(0, "Transaksi berhasil", transaction));
        } catch (RuntimeException e) {
            int status = 102;
            if (e.getMessage().contains("Service atau Layanan tidak ditemukan")) {
                status = 102;
            } else if (e.getMessage().contains("Saldo tidak mencukupi")) {
                status = 102;
            } else if (e.getMessage().contains("Token")) {
                status = 108;
            }
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(status, e.getMessage(), null));
        }
    }

    @GetMapping("/transaction/history")
    public ResponseEntity<?> getTransactionHistory(@RequestParam(defaultValue = "0") Integer offset,@RequestParam(required = false) Integer limit) {
        try {
            TransactionHistoryResponse history = transactionService.getTransactionHistory(offset, limit);
            return ResponseEntity.ok(new ApiResponse<>(0, "Get History Berhasil", history));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(108, e.getMessage(), null));
        }
    }
}
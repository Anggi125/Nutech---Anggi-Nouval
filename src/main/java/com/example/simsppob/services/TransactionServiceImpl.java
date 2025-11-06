package com.example.simsppob.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.simsppob.dto.TopUpRequest;
import com.example.simsppob.dto.TransactionHistoryResponse;
import com.example.simsppob.dto.TransactionResponse;
import com.example.simsppob.jwt.JwtUtil;
import com.example.simsppob.models.Transaction;
import com.example.simsppob.models.User;
import com.example.simsppob.repositories.ServiceRepository;
import com.example.simsppob.repositories.TransactionRepository;
import com.example.simsppob.repositories.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Long getBalance() {
        String email = jwtUtil.getEmailFromCurrentRequest();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        return user.getBalance();
    }

    @Override
    public Long topUp(TopUpRequest amount) {
        String email = jwtUtil.getEmailFromCurrentRequest();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        user.setBalance(user.getBalance() + amount.getTopUpAmount());
        userRepository.save(user);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionType("TOPUP");
        transaction.setTotalAmount(amount.getTopUpAmount());
        transaction.setDescription("Top Up balance");
        transaction.setInvoiceNumber(generateInvoiceNumber());
        transaction.setCreatedOn(LocalDateTime.now());
        transactionRepository.save(transaction);

        return user.getBalance();
    }

    @Override
    public TransactionResponse createTransaction(String serviceCode) {
        String email = jwtUtil.getEmailFromCurrentRequest();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        com.example.simsppob.models.Service service = serviceRepository.findByServiceCode(serviceCode)
                .orElseThrow(() -> new RuntimeException("Service atau Layanan tidak ditemukan"));

        if (user.getBalance() < service.getServiceTariff()) {
            throw new RuntimeException("Saldo tidak mencukupi");
        }

        // Deduct balance
        user.setBalance(user.getBalance() - service.getServiceTariff());
        userRepository.save(user);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionType("PAYMENT");
        transaction.setServiceCode(service.getServiceCode());
        transaction.setServiceName(service.getServiceName());
        transaction.setTotalAmount(service.getServiceTariff());
        transaction.setDescription(service.getServiceName());
        transaction.setInvoiceNumber(generateInvoiceNumber());
        transaction.setCreatedOn(LocalDateTime.now());
        transactionRepository.save(transaction);

        TransactionResponse response = new TransactionResponse();
        response.setInvoiceNumber(transaction.getInvoiceNumber());
        response.setServiceCode(transaction.getServiceCode());
        response.setServiceName(transaction.getServiceName());
        response.setTransactionType(transaction.getTransactionType());
        response.setTotalAmount(transaction.getTotalAmount());
        response.setCreatedOn(transaction.getCreatedOn());

        return response;
    }

    private String generateInvoiceNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String date = LocalDate.now().format(formatter);
        String random = String.format("%03d", new Random().nextInt(1000));
        return "INV" + date + "-" + random;
    }

    @Override
    public TransactionHistoryResponse getTransactionHistory(Integer offset, Integer limit) {
        String email = jwtUtil.getEmailFromCurrentRequest();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        List<Transaction> transactions;
        if (limit != null) {
            Pageable pageable = PageRequest.of(offset != null ? offset : 0, limit);
            transactions = transactionRepository.findByUserOrderByCreatedOnDesc(user, pageable);
        } else {
            transactions = transactionRepository.findByUserOrderByCreatedOnDesc(user);
        }

        List<TransactionHistoryResponse.TransactionRecord> records = transactions.stream()
                .map(this::convertToRecord)
                .collect(Collectors.toList());

        TransactionHistoryResponse response = new TransactionHistoryResponse();
        response.setOffset(offset);
        response.setLimit(limit);
        response.setRecords(records);

        return response;
    }

    private TransactionHistoryResponse.TransactionRecord convertToRecord(Transaction transaction) {
        TransactionHistoryResponse.TransactionRecord record = new TransactionHistoryResponse.TransactionRecord();
        record.setInvoiceNumber(transaction.getInvoiceNumber());
        record.setTransactionType(transaction.getTransactionType());
        record.setDescription(transaction.getDescription());
        record.setTotalAmount(transaction.getTotalAmount());
        record.setCreatedOn(transaction.getCreatedOn());
        return record;
    }

}
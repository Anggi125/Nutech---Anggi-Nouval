package com.example.simsppob.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simsppob.models.Transaction;
import com.example.simsppob.models.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByCreatedOnDesc(User user);

    List<Transaction> findByUserOrderByCreatedOnDesc(User user, Pageable pageable);
}

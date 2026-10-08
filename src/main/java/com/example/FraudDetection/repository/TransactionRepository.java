package com.example.FraudDetection.repository;

import com.example.FraudDetection.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
@Query("SELECT DISTINCT t.location FROM Transaction t WHERE t.user.id = :userId")
List<String> findDistinctLocationByUserId(@Param("userId") Long userId);

@Query("SELECT DISTINCT t.deviceId FROM Transaction t WHERE t.user.id = :userId")
List<String> findDistinctDeviceIdByUserId(@Param("userId") Long userId);
@Query("""
    SELECT COUNT(t)
    FROM Transaction t
    WHERE t.user.id = :userId
    AND t.createdAt >= :startTime
""")
long countRecentTransactions(
        @Param("userId") Long userId,
        @Param("startTime") LocalDateTime startTime
);
List<Transaction> findByUserId(Long userId);
}

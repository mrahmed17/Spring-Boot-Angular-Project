package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.PaymentReceipt;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentReceiptRepository extends JpaRepository <PaymentReceipt, Long> {

    List<PaymentReceipt> findAllByPaymentReceiver_Id(Long userId);

    @Query("SELECT pr FROM PaymentReceipt pr WHERE pr.status = :status")
    List<PaymentReceipt> findAllByStatus(@Param("status") boolean status);

    @Query("SELECT pr FROM PaymentReceipt pr WHERE pr.paymentDate BETWEEN :startDate AND :endDate")
    List<PaymentReceipt> findAllByPaymentDateBetween(@Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);


}

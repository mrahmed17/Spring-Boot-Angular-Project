package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.PaymentReceipt;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.PaymentReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Project: M. R. Ahmed
 * @Author: Running Boot
 * @Created at:  9/30/2024
 */

@Service
public class PaymentReceiptService {

    @Autowired
    private PaymentReceiptRepository paymentReceiptRepository;

    public PaymentReceipt createPaymentReceipt(PaymentReceipt paymentReceipt) {
        return paymentReceiptRepository.save(paymentReceipt);
    }

    public PaymentReceipt updatePaymentReceipt(Long receiptId, PaymentReceipt updatedReceipt) {
        Optional<PaymentReceipt> existingReceipt = paymentReceiptRepository.findById(receiptId);
        if (existingReceipt.isPresent()) {
            PaymentReceipt receipt = existingReceipt.get();
            receipt.setPaymentDate(updatedReceipt.getPaymentDate());
            receipt.setTotalPaidAmount(updatedReceipt.getTotalPaidAmount());
            receipt.setDeductions(updatedReceipt.getDeductions());
            receipt.setNetPaidAmount(updatedReceipt.getNetPaidAmount());
            receipt.setStatus(updatedReceipt.isStatus());
            receipt.setPaymentReceiver(updatedReceipt.getPaymentReceiver());
            receipt.setSalary(updatedReceipt.getSalary());
            return paymentReceiptRepository.save(receipt);
        }
        return null;
    }

    public void deletePaymentReceipt(Long receiptId) {
        paymentReceiptRepository.deleteById(receiptId);
    }

    public List<PaymentReceipt> getPaymentReceiptsByPaymentReceiver(User paymentReceiver) {
        return paymentReceiptRepository.findByPaymentReceiver(paymentReceiver);
    }

    public List<PaymentReceipt> getPaymentReceiptsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentReceiptRepository.findByPaymentDateBetween(startDate, endDate);
    }

    public List<PaymentReceipt> getAllPaymentReceipts() {
        return paymentReceiptRepository.findAll();
    }

    public Double getTotalAmountByYear(int year) {
        return paymentReceiptRepository.findTotalAmountByYear(year);
    }

    public Double getTotalAmountByMonth(int month) {
        return paymentReceiptRepository.findTotalAmountByMonth(month);
    }

    public PaymentReceipt getLatestReceiptByPaymentReceiver(User paymentReceiver) {
        Optional<PaymentReceipt> latestReceipt = paymentReceiptRepository.findLatestReceiptByPaymentReceiver(paymentReceiver);
        return latestReceipt.orElse(null);
    }

    public List<PaymentReceipt> getPaymentReceiptsByFullName(String name) {
        return paymentReceiptRepository.findByUserFullName(name);
    }

    public List<PaymentReceipt> getPaymentReceiptsByStatus(String status) {
        return paymentReceiptRepository.findByPaymentStatus(status);
    }

    public Double getTotalPaymentsByUserId(Long userId) {
        return paymentReceiptRepository.findTotalPaymentsByUserId(userId);
    }


}

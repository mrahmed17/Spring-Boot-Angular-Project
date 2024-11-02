package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.PaymentReceipt;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.PaymentReceiptService;
import com.mrahmed.HRandPayrollManagementSystem.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Project: Running Boot
 * @Author: M. R. Ahmed
 * @Created at: 9/30/2024
 */

@RestController
@RequestMapping("/api/payment-receipts")
@CrossOrigin("*")
public class PaymentReceiptRestController {

    @Autowired
    private PaymentReceiptService paymentReceiptService;

    @Autowired
    private UserService userService;

    // Create a new payment receipt
    @PostMapping ("/create")
    public ResponseEntity<PaymentReceipt> createPaymentReceipt(@RequestBody PaymentReceipt paymentReceipt) {
        PaymentReceipt createdReceipt = paymentReceiptService.createPaymentReceipt(paymentReceipt);
        return ResponseEntity.ok(createdReceipt);
    }

    // Update an existing payment receipt
    @PutMapping("/update/{receiptId}")
    public ResponseEntity<PaymentReceipt> updatePaymentReceipt(@PathVariable Long receiptId, @RequestBody PaymentReceipt updatedReceipt) {
        PaymentReceipt updated = paymentReceiptService.updatePaymentReceipt(receiptId, updatedReceipt);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a payment receipt
    @DeleteMapping("/delete/{receiptId}")
    public ResponseEntity<Void> deletePaymentReceipt(@PathVariable Long receiptId) {
        paymentReceiptService.deletePaymentReceipt(receiptId);
        return ResponseEntity.noContent().build();
    }

    // Get payment receipts by payment receiver
    @GetMapping("/payment-receiver/{userId}")
    public ResponseEntity<List<PaymentReceipt>> getPaymentReceiptsByReceiver(@PathVariable Long userId) {
        User paymentReceiver = userService.findUserById(userId);
        return ResponseEntity.ok(paymentReceiptService.getPaymentReceiptsByPaymentReceiver(paymentReceiver));
    }

    // Get payment receipts by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentReceipt>> getPaymentReceiptsByDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<PaymentReceipt> receipts = paymentReceiptService.getPaymentReceiptsByDateRange(startDate, endDate);
        return ResponseEntity.ok(receipts);
    }


    @GetMapping("/all")
    public ResponseEntity<List<PaymentReceipt>> getAllPaymentReceipts() {
        List<PaymentReceipt> receipts = paymentReceiptService.getAllPaymentReceipts();
        return ResponseEntity.ok(receipts);
    }


    // Get total amount by year
    @GetMapping("/total-by-year")
    public ResponseEntity<Double> getTotalAmountByYear(@RequestParam int year) {
        Double totalAmount = paymentReceiptService.getTotalAmountByYear(year);
        return ResponseEntity.ok(totalAmount);
    }

    // Get total amount by month
    @GetMapping("/total-by-month")
    public ResponseEntity<Double> getTotalAmountByMonth(@RequestParam int month) {
        Double totalAmount = paymentReceiptService.getTotalAmountByMonth(month);
        return ResponseEntity.ok(totalAmount);
    }

    @GetMapping("/latest-receipt/payment-receiver/{userId}")
    public ResponseEntity<PaymentReceipt> getLatestReceiptByPaymentReceiver(@PathVariable Long userId) {
        User paymentReceiver = userService.findUserById(userId);
        PaymentReceipt latestReceipt = paymentReceiptService.getLatestReceiptByPaymentReceiver(paymentReceiver);

        if (latestReceipt != null) {
            return ResponseEntity.ok(latestReceipt);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get payment receipts by user full name
    @GetMapping("/search-by-name")
    public ResponseEntity<List<PaymentReceipt>> getPaymentReceiptsByFullName(@RequestParam String name) {
        List<PaymentReceipt> receipts = paymentReceiptService.getPaymentReceiptsByFullName(name);
        return ResponseEntity.ok(receipts);
    }

    // Get payment receipts by status
    @GetMapping("/status")
    public ResponseEntity<List<PaymentReceipt>> getPaymentReceiptsByStatus(@RequestParam String status) {
        List<PaymentReceipt> receipts = paymentReceiptService.getPaymentReceiptsByStatus(status);
        return ResponseEntity.ok(receipts);
    }

    // Get total payments by userId
    @GetMapping("/total-by-user/{userId}")
    public ResponseEntity<Double> getTotalPaymentsByUserId(@PathVariable Long userId) {
        Double totalPayments = paymentReceiptService.getTotalPaymentsByUserId(userId);
        return ResponseEntity.ok(totalPayments);
    }



}

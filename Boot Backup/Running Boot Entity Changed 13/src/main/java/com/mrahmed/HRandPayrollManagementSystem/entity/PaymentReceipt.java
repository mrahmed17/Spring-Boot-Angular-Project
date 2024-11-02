package com.mrahmed.HRandPayrollManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "paymentreceipts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime paymentDate;
    private double totalPaidAmount;
    private double deductions;
    private double netPaidAmount;
    private boolean status; // Payment status is Paid or Unpaid

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentReceiverId")
    private User paymentReceiver;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salaryId")
    private Salary salary;



}

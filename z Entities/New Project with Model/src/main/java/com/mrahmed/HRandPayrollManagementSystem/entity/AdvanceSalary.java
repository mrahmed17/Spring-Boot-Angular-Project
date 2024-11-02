package com.mrahmed.HRandPayrollManagementSystem.entity;

import com.mrahmed.HRandPayrollManagementSystem.allenum.ApprovalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdvanceSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @DecimalMin("0.0")
    private Double amount;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Temporal(TemporalType.DATE)
    private Date repaymentBackSchedule;

    private Double repaymentBackAmount;


}

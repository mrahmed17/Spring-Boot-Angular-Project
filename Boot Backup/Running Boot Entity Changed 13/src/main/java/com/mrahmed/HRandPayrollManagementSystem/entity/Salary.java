package com.mrahmed.HRandPayrollManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "salaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime paymentDate;
    private double medicare;
    private double providentFund;
    private double insurance;
    private double transportAllowance;
    private double telephoneSubsidy;
    private double utilityAllowance;
    private double domesticAllowance;
    private double lunchAllowance;
    private double tax;
    private double netSalary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advanceSalaryId")
    private AdvanceSalary advanceSalary;

    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "bonusId")
    private Bonus bonuses;

    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "leaveId")
    private Leave leaves;



}


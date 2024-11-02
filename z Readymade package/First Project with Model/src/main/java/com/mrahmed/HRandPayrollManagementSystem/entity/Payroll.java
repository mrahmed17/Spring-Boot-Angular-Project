package com.mrahmed.HRandPayrollManagementSystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "salaries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date paymentDate;
    private double medicare;
    private double providentFund; // baseSalary * (2% = 0.02)
    private double insurance;
    private double netSalary;
    private boolean overTime; //(If Yes then add 1days salary=(baseSalary/4 week /5 days)
//    private Date paymentCycle; // Represents the month and year for the payment cycle (e.g., 2024-09 for September 2024)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User basicSalary;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "advancesalaryId")
    private AdvanceSalary advanceSalary;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bonusId")
    private Bonus bonus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "leaveId")
    private Leave leave;

}


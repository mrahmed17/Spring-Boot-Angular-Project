package com.mrahmed.HRandPayrollManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private BigDecimal medicare;
    private BigDecimal providentFund; // baseSalary * (2% = 0.02)
    private BigDecimal insurance;
    private BigDecimal transportAllowance;
    private BigDecimal telephoneSubsidy;
    private BigDecimal utilityAllowance;
    private BigDecimal domesticAllowance;
    private BigDecimal lunchAllowance;
    private BigDecimal netSalary;
    private int year;
    private BigDecimal tax;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "overTime")
    private List<Attendance> overTime; //Working hours are 8. If attendance checks over the 8 hours, it will count as overtime.
    //  Overtime salary calculation = (basicSalary from user divided 4 week * 5 days * 8 hours)

    @Enumerated(EnumType.STRING)
    private Month payrollMonth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "advanceSalary_id")
    private AdvanceSalary advanceSalary;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bonus_id")
    private List<Bonus> bonuses;

    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_id")
    private List<Leave> leaves;


}


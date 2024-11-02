//package com.mrahmed.HRandPayrollManagementSystem.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Table(name = "salaries")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Salary {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private LocalDateTime paymentDate;
//    private BigDecimal medicare;
//    private BigDecimal providentFund; // baseSalary * (2% = 0.02)
//    private BigDecimal insurance;
//    private BigDecimal transportAllowance;
//    private BigDecimal telephoneSubsidy;
//    private BigDecimal utilityAllowance;
//    private BigDecimal domesticAllowance;
//    private BigDecimal lunchAllowance;
//    private boolean overTime; //(If Yes then add 1days salary=(baseSalary/4 week /5 days)
//    private BigDecimal netSalary;
//    private int year;
//    private BigDecimal tax;
//
//    @Enumerated(EnumType.STRING)
//    private Month payrollMonth;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "advanceSalaryId")
//    private Advance advanceSalary;
//
//    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "bonusId")
//    private List<Bonus> bonuses;
//
//    @OneToMany (fetch = FetchType.LAZY)
//    @JoinColumn(name = "leaveId")
//    private List<Leave> leaves;
//
//
//}
//

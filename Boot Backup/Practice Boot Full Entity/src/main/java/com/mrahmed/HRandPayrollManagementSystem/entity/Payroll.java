//package com.mrahmed.HRandPayrollManagementSystem.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Table(name = "payrolls")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Payroll {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private LocalDateTime paymentDate;
//    private double medicare;
//    private double providentFund; // baseSalary * (2% = 0.02)
//    private double insurance;
//    private double transportAllowance;
//    private double telephoneSubsidy;
//    private double utilityAllowance;
//    private double domesticAllowance;
//    private double lunchAllowance;
//    private boolean overTime; //(If Yes then add 1days salary=(baseSalary/4 week /5 days)
//    private double netSalary;
//    private int year;
//    private double tax;
//
//    @Enumerated(EnumType.STRING)
//    private Month payrollMonth;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "advancesalaryId")
//    private AdvanceSalary advanceSalary;
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

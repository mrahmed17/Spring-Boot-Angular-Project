package com.mrahmed.HRandPayrollManagementSystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "advancesalaries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal advanceSalary;
    private String reason;
    private LocalDateTime advanceDate;
    private int year;

    @Enumerated(EnumType.STRING)
    private Month advanceMonth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}

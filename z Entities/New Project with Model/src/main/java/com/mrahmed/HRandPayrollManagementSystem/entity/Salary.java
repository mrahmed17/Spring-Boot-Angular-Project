package com.mrahmed.HRandPayrollManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

/**
 * @Project: First Project with Model
 * @Author: M. R. Ahmed
 * @Created at: 10/1/2024
 */

public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @DecimalMin("0.0")
    private Double basicSalary;

    private Double overtimeSalary;

    private Boolean isCurrent = true;

    private Double bonus;

    private Double advanceSalary;

    @Transient
    private Double grossSalary;

    private Double deductions;

    @Transient
    private Double netSalary;

    @Temporal(TemporalType.DATE)
    private Date date;

    // the other fields, grossSalary and netSalary are Calculated from service

}

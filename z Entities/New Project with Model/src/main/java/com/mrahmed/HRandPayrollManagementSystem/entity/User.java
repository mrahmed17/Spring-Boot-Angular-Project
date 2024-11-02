package com.mrahmed.HRandPayrollManagementSystem.entity;

import com.mrahmed.HRandPayrollManagementSystem.allenum.Role;
import com.mrahmed.HRandPayrollManagementSystem.allenum.WorkSchedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @NotBlank
    @Column(unique = true)
    private String userName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private WorkSchedule workSchedule;

    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    @DecimalMin("0.0")
    private Double basicSalary;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Min(0)
    private Integer leaveBalance;

}



package com.mrahmed.HRandPayrollManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String photo;

    private String address;

    @Email
    @Column(unique = true)
    private String contactEmail;

    @Temporal(TemporalType.DATE)
    private Date establishmentDate;



}

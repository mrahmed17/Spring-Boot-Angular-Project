package com.mrahmed.HRandPayrollManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "leaves")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String leaveReason; // Reserve or Sick
    private Date startDate;
    private Date endDate;
    private double remainingLeave; // Total 25 days: (Reserve 10 + sick 15), remaining: (total - (end - start))
    private Date requestDate;
    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user; // id, name, contact


}

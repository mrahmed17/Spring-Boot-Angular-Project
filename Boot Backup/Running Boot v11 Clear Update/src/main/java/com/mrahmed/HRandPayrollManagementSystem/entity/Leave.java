package com.mrahmed.HRandPayrollManagementSystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaves")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime requestDate;
    private String reason;
    private int remainingLeave; // total 25 days in a year: (reserve 10 + sick 15 days), remainingCalculation(total -(endDate-startDate))
    private int year;

    @Enumerated(EnumType.STRING)
    private Month leaveMonth;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "isUnpaid", nullable = false)
    private boolean isUnpaid;


}


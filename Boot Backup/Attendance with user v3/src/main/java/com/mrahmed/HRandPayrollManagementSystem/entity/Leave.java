//package com.mrahmed.HRandPayrollManagementSystem.entity;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "leaves")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Leave {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private LocalDateTime requestDate;
//    private int remainingLeave; // total 25 days: (reserve 10 + sick 15 days), remainingcalculation(total -(end-startdate))
//    private int year;
//
//    @Enumerated(EnumType.STRING)
//    private Month leaveMonth;
//
//    @Enumerated(EnumType.STRING)
//    private LeaveType leaveType;
//
//    @Enumerated(EnumType.STRING)
//    private RequestStatus requestStatus;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", nullable = false)
//    private User user;
//}
//

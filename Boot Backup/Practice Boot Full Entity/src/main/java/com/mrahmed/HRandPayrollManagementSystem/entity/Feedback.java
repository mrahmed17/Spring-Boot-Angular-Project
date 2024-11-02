//package com.mrahmed.HRandPayrollManagementSystem.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "feedbacks")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Feedback {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private LocalDateTime date;
//    private String rating;
//    private String comment;
//
//    @Enumerated(EnumType.STRING)
//    private Month feedbackMonth;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
//
//
//}

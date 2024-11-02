package com.mrahmed.HRandPayrollManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;

    @Column(name = "isLate")
    private boolean late;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
  //ManyToMany

    //Working hours are 8. If attendance checks over the 8 hours, it will count as overtime.
    //  Overtime salary calculation = (basicSalary from user divided 4 week * 5 days * 8 hours)


}

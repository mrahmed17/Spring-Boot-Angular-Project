package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository <Attendance, Long> {

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user.id = :userId AND a.month = :month")
    int findAttendanceByUserAndMonth(@Param("userId") Long userId, @Param("month") Month month);

    List<Attendance> findByUserId(Long userId);


}

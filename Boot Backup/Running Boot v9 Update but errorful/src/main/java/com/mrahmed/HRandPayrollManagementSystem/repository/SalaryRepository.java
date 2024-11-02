package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {


    // Get salary records by user ID and year
    @Query("SELECT s FROM Salary s WHERE s.user.id = :userId AND s.year = :year")
    List<Salary> findSalariesByUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    // Get salary records by user ID, year, and month
    @Query("SELECT s FROM Salary s WHERE s.user.id = :userId AND s.year = :year AND s.payrollMonth = :payrollMonth")
    List<Salary> findSalariesByUserYearAndMonth(@Param("userId") Long userId, @Param("year") int year, @Param("payrollMonth") Month payrollMonth);

    // Get the latest salary record for a user
    @Query("SELECT s FROM Salary s WHERE s.user.id = :userId ORDER BY s.paymentDate DESC")
    List<Salary> findLatestSalaryByUser(@Param("userId") Long userId);

    // Get total salary payments for a specific user in a year
    @Query("SELECT SUM(s.netSalary) FROM Salary s WHERE s.user.id = :userId AND s.year = :year")
    double getTotalSalaryByUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    // Get salaries within a specific date range
    @Query("SELECT s FROM Salary s WHERE s.paymentDate BETWEEN :startDate AND :endDate")
    List<Salary> findSalariesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Get salaries for a specific month and year
    @Query("SELECT s FROM Salary s WHERE s.payrollMonth = :payrollMonth AND s.year = :year")
    List<Salary> findSalariesByMonthAndYear(@Param("payrollMonth") Month payrollMonth, @Param("year") int year);

    // Get total overtime hours for a user in a specific period
    @Query(value = "SELECT SUM(TIMESTAMPDIFF(HOUR, a.clockInTime, a.clockOutTime) - 8) " +
            "FROM Attendance a WHERE a.user_id = :userId AND a.date BETWEEN :startDate AND :endDate " +
            "AND TIMESTAMPDIFF(HOUR, a.clockInTime, a.clockOutTime) > 8",
            nativeQuery = true)
    double getTotalOvertimeHoursByUserAndDateRange(@Param("userId") Long userId,
                                                       @Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);

    // Get total overtime salary for a user in a specific period
    @Query(value = "SELECT SUM((s.netSalary / 4 / 5 / 8) * (TIMESTAMPDIFF(HOUR, a.clockInTime, a.clockOutTime) - 8)) " +
            "FROM Salary s JOIN User u ON s.user_id = u.id JOIN Attendance a ON u.id = a.user_id " +
            "WHERE u.id = :userId AND a.date BETWEEN :startDate AND :endDate " +
            "AND TIMESTAMPDIFF(HOUR, a.clockInTime, a.clockOutTime) > 8",
            nativeQuery = true)
    double getTotalOvertimeSalaryByUserAndDateRange(@Param("userId") Long userId,
                                                        @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate);


}

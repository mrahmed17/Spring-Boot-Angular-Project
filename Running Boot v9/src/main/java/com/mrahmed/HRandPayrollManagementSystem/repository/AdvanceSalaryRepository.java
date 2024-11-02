package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.AdvanceSalary;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdvanceSalaryRepository extends JpaRepository<AdvanceSalary, Long> {

    // Find advance salaries by user and year
    @Query("SELECT a FROM AdvanceSalary a WHERE a.user.id = :userId AND a.year = :year")
    List<AdvanceSalary> findByUserIdAndYear(@Param("userId") Long userId, @Param("year") int year);

    // Find advance salaries by user, year, and month
    @Query("SELECT a FROM AdvanceSalary a WHERE a.user.id = :userId AND a.year = :year AND a.advanceMonth = :month")
    List<AdvanceSalary> findByUserIdYearAndMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") Month month);

    // Calculate total advance salary for a specific user and year
    @Query("SELECT SUM(a.advanceSalary) FROM AdvanceSalary a WHERE a.user.id = :userId AND a.year = :year")
    BigDecimal getTotalAdvanceSalaryByUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    // Find advance salaries within a specific date range
    @Query("SELECT a FROM AdvanceSalary a WHERE a.advanceDate BETWEEN :startDate AND :endDate")
    List<AdvanceSalary> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Find advance salaries for a specific month and year
    @Query("SELECT a FROM AdvanceSalary a WHERE a.advanceMonth = :month AND a.year = :year")
    List<AdvanceSalary> findByMonthAndYear(@Param("month") Month month, @Param("year") int year);

    // Find latest advance salary record for a user
    @Query("SELECT a FROM AdvanceSalary a WHERE a.user.id = :userId ORDER BY a.advanceDate DESC")
    List<AdvanceSalary> findLatestAdvanceSalaryByUser(@Param("userId") Long userId);


}

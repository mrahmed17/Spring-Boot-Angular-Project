package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.entity.Salary;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findById(Long id);

    @Query("SELECT s FROM Salary s WHERE LOWER(s.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Salary> findByFullName(@Param("name") String name);

    @Query("SELECT s FROM Salary s WHERE s.user.email = :email")
    Optional<Salary> findByEmail(@Param("email") String email);

    @Query("SELECT s FROM Salary s WHERE s.payrollMonth = :month")
    List<Salary> findByPayrollMonth(@Param("month") Month month);

    @Query("SELECT s FROM Salary s WHERE s.user.id = :userId ")
    List<Salary> findSalariesByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Salary s WHERE s.user.id = :userId ORDER BY s.paymentDate DESC")
    List<Salary> findLatestSalaryByUser(@Param("userId") Long userId);

    @Query("SELECT s FROM Salary s WHERE s.user.id = :userId AND s.year = :year")
    List<Salary> findSalariesByUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    @Query("SELECT SUM(s.netSalary) FROM Salary s WHERE s.payrollMonth = :month")
    double getTotalSalaryByMonth(@Param("month") Month month);

    @Query("SELECT SUM(s.netSalary) FROM Salary s WHERE s.year = :year")
    double getTotalSalaryByYear(@Param("year") int year);

    @Query("SELECT SUM(s.netSalary) FROM Salary s WHERE s.user.id = :userId AND s.year = :year")
    double getTotalSalaryByUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    @Query("SELECT s FROM Salary s WHERE s.paymentDate BETWEEN :startDate AND :endDate")
    List<Salary> findSalariesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT u FROM User u JOIN Salary s ON u.id = s.user.id WHERE s.payrollMonth = :month AND s.netSalary = (SELECT MAX(ss.netSalary) FROM Salary ss WHERE ss.payrollMonth = :month)")
    Optional<User> findUserWithHighestMonthlySalary(@Param("month") Month month);

    @Query("SELECT u FROM User u JOIN Salary s ON u.id = s.user.id WHERE s.year = :year AND s.netSalary = (SELECT MAX(ss.netSalary) FROM Salary ss WHERE ss.year = :year)")
    Optional<User> findUserWithHighestYearlySalary(@Param("year") int year);


}

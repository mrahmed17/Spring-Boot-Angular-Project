package com.mrahmed.HRandPayrollManagementSystem.repository;

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

    List<Salary> findAllByUser_Id(Long userId);

    @Query("SELECT s FROM Salary s WHERE s.paymentDate BETWEEN :startDate AND :endDate")
    List<Salary> findAllByPaymentDateBetween(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s FROM Salary s WHERE s.advanceSalary.id = :advanceSalaryId")
    List<Salary> findAllByAdvanceSalaryId(@Param("advanceSalaryId") Long advanceSalaryId);

    @Query("SELECT s FROM Salary s WHERE s.bonuses.id = :bonusId")
    List<Salary> findAllByBonusId(@Param("bonusId") Long bonusId);

    @Query("SELECT s FROM Salary s WHERE s.leaves.id = :leaveId")
    List<Salary> findAllByLeaveId(@Param("leaveId") Long leaveId);


}

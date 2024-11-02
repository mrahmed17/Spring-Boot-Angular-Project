package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.AdvanceSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvanceSalaryRepository extends JpaRepository<AdvanceSalary, Long> {

    // Find all advance salaries by user ID
    List<AdvanceSalary> findAllByUser_Id(Long userId);

    // Find an advance salary by its ID
    AdvanceSalary findById(long id);

    // Find all advance salaries greater than or equal to a certain amount
    @Query("SELECT a FROM AdvanceSalary a WHERE a.advanceSalary >= :amount")
    List<AdvanceSalary> findByAdvanceSalaryGreaterThanOrEqual(@Param("amount") double amount);

    // Count total advance salaries for a specific user
    @Query("SELECT COUNT(a) FROM AdvanceSalary a WHERE a.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);


}

package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    // Find all bonuses for a specific user
    List<Bonus> findAllByUser_Id(Long userId);

    // Find bonuses by date range for a specific user
    @Query("SELECT b FROM Bonus b WHERE b.user.id = :userId AND b.bonusDate BETWEEN :startDate AND :endDate")
    List<Bonus> findBonusesByUserIdAndDateRange(@Param("userId") Long userId,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    // Count total bonuses for a specific user
    @Query("SELECT COUNT(b) FROM Bonus b WHERE b.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

}

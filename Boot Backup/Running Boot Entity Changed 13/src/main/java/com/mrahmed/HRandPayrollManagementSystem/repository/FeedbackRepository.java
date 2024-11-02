package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository <Feedback, Long> {


    // Find all feedbacks for a specific user
    List<Feedback> findAllByUser_Id(Long userId);

    // Find feedback by date range for a specific user
    @Query("SELECT f FROM Feedback f WHERE f.user.id = :userId AND f.date BETWEEN :startDate AND :endDate")
    List<Feedback> findFeedbackByUserIdAndDateRange(@Param("userId") Long userId,
                                                    @Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate);

    // Count total feedbacks for a specific user
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);


}

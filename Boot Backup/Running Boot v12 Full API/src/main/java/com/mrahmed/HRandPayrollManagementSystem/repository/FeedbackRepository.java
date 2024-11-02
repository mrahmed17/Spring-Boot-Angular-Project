package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Feedback;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository <Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.user.email = :email")
    List<Feedback> findFeedbacksByEmail(@Param("email") String email);

    @Query("SELECT f FROM Feedback f WHERE LOWER(f.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Feedback> findFeedbacksByName(@Param("name") String name);

    @Query("SELECT f FROM Feedback f WHERE f.user.id = :userId")
    List<Feedback> findFeedbacksByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Feedback f WHERE f.feedbackMonth = :month")
    List<Feedback> findFeedbacksByMonth(@Param("month") Month month);

    @Query("SELECT f FROM Feedback f WHERE f.date BETWEEN :startDate AND :endDate")
    List<Feedback> findFeedbacksByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT f FROM Feedback f WHERE f.user.id = :userId ORDER BY f.date DESC")
    List<Feedback> findLatestFeedbackByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.user.id = :userId")
    int countFeedbacksByUserId(@Param("userId") Long userId);



}

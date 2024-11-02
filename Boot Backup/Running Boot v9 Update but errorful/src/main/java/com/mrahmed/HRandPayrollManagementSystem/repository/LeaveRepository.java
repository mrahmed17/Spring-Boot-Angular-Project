package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Leave;
import com.mrahmed.HRandPayrollManagementSystem.entity.LeaveType;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.year = :year")
    List<Leave> findLeavesByUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    @Query("SELECT l FROM Leave l WHERE l.requestStatus = 'PENDING'")
    List<Leave> findPendingLeaveRequests();

    @Query("SELECT l FROM Leave l WHERE l.leaveMonth = :month AND l.year = :year")
    List<Leave> findLeavesByMonthAndYear(@Param("month") Month month, @Param("year") int year);

    @Query("SELECT l FROM Leave l WHERE l.leaveType = :leaveType")
    List<Leave> findLeavesByType(@Param("leaveType") LeaveType leaveType);

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.requestStatus = 'APPROVED'")
    List<Leave> findApprovedLeavesByUser(@Param("userId") Long userId);

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.startDate >= :startDate AND l.endDate <= :endDate")
    List<Leave> findLeavesByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(l.remainingLeave) FROM Leave l WHERE l.user.id = :userId AND l.year = :year")
    Integer calculateRemainingLeavesByUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId AND l.leaveType IN (:leaveTypes) AND l.year = :year")
    int getTotalUnpaidLeaveDays(@Param("userId") Long userId, @Param("leaveTypes") List<LeaveType> leaveTypes, @Param("year") int year);

    @Query("SELECT l FROM Leave l WHERE l.reason = :reason")
    List<Leave> findLeavesByReason(@Param("reason") String reason);

    @Query("SELECT l FROM Leave l WHERE l.startDate BETWEEN :startDate AND :endDate")
    List<Leave> findLeavesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Get total unpaid leave days for a specific user and leave types
    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId AND l.isUnpaid = true AND l.leaveType IN :leaveTypes")
    int getTotalUnpaidLeaveDays(@Param("userId") Long userId, @Param("leaveTypes") List<LeaveType> leaveTypes);

    // Find leaves by user ID
    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId")
    List<Leave> findLeavesByUserId(@Param("userId") Long userId);

    // Find unpaid leaves for a user
    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.isUnpaid = true")
    List<Leave> findUnpaidLeavesByUserId(@Param("userId") Long userId);

    // Count all leaves for a specific user
    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId")
    int countLeavesByUserId(@Param("userId") Long userId);
}

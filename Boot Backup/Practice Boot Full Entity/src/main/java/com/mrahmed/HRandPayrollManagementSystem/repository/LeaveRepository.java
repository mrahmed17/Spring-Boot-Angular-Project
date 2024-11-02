//package com.mrahmed.HRandPayrollManagementSystem.repository;
//
//import com.mrahmed.HRandPayrollManagementSystem.entity.Leave;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//public interface LeaveRepository extends JpaRepository<Leave, Long> {
//
//
//    // Find leaves for a specific user
//    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId")
//    List<Leave> findByUserId(@Param("userId") Long userId);
//
//    // Find approved leave requests for a specific employee
//    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.requestStatus = 'APPROVED'")
//    List<Leave> findApprovedLeavesByUserId(@Param("userId") Long userId);
//
//    // Find leave requests for a specific date range
//    @Query("SELECT l FROM Leave l WHERE (l.startDate BETWEEN :startDate AND :endDate) OR (l.endDate BETWEEN :startDate AND :endDate) OR (l.startDate <= :startDate AND l.endDate >= :endDate)")
//    List<Leave> findLeavesInDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//
//    // Find overlapping leaves with a specific date range
//    @Query("SELECT l FROM Leave l WHERE (l.startDate <= :endDate AND l.endDate >= :startDate)")
//    List<Leave> findOverlappingLeaves(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//
//    // Find all pending leave requests
//    @Query("SELECT l FROM Leave l WHERE l.requestStatus = 'PENDING'")
//    List<Leave> findAllPendingLeaveRequests();
//
//    // Find all leave requests by status
//    @Query("SELECT l FROM Leave l WHERE l.requestStatus = :status")
//    List<Leave> findAllLeavesByStatus(@Param("status") String status);
//
//
//}
//

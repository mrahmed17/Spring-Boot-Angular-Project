package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByFullName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.isUnpaid = true")
    List<Leave> findUnpaidLeavesByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId")
    int countLeavesByUserId(@Param("userId") Long userId);

    @Query("SELECT l FROM Leave l WHERE l.requestDate = :date")
    List<Leave> findLeaveByRequestDate(@Param("date") LocalDate date);

    @Query("SELECT l FROM Leave l WHERE l.requestStatus = :status")
    List<Leave> findLeaveByRequestStatus(@Param("status") RequestStatus status);

    @Query("SELECT l FROM Leave l WHERE l.year = :year")
    List<Leave> findAllLeaveByYear(@Param("year") int year);

    @Query("SELECT l FROM Leave l WHERE l.isUnpaid = true")
    List<Leave> findUnpaidLeaves();

    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId AND l.requestStatus = :status")
    long countLeavesByUserAndStatus(@Param("userId") Long userId, @Param("status") RequestStatus status);

    @Query("SELECT l FROM Leave l WHERE l.leaveMonth = :month")
    List<Leave> findLeavesByMonth(@Param("month") Month month);

    @Query("SELECT l FROM Leave l WHERE l.requestStatus = 'PENDING'")
    List<Leave> findPendingLeaveRequests();

    @Query("SELECT l FROM Leave l WHERE l.requestStatus = 'APPROVED'")
    List<Leave> findApprovedLeaveRequests();

    @Query("SELECT l FROM Leave l WHERE l.requestStatus = 'REJECTED'")
    List<Leave> findRejectedLeaveRequests();

    @Query("SELECT l FROM Leave l WHERE l.leaveType = :leaveType")
    List<Leave> findLeavesByType(@Param("leaveType") LeaveType leaveType);

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.requestStatus = 'APPROVED'")
    List<Leave> findApprovedLeavesByUser(@Param("userId") Long userId);

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.requestStatus = 'REJECTED'")
    List<Leave> findRejectedLeavesByUser(@Param("userId") Long userId);

    @Query("SELECT l FROM Leave l WHERE l.startDate >= :startDate AND l.endDate <= :endDate")
    List<Leave> findLeavesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT SUM(l.remainingLeave) FROM Leave l WHERE l.user.id = :userId")
    Integer calculateRemainingLeavesByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId AND l.leaveType IN (:leaveTypes) AND l.year = :year")
    int getTotalUnpaidLeaveDays(@Param("userId") Long userId, @Param("leaveTypes") List<LeaveType> leaveTypes, @Param("year") int year);


    @Query("SELECT l FROM Leave l WHERE l.reason = :reason")
    List<Leave> findLeavesByReason(@Param("reason") String reason);

    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId AND l.isUnpaid = true AND l.leaveType IN :leaveTypes")
    int getTotalUnpaidLeaveDays(@Param("userId") Long userId, @Param("leaveTypes") List<LeaveType> leaveTypes);


    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId")
    List<Leave> findLeavesByUserId(@Param("userId") Long userId);



}

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

    // Find all leaves for a specific user
    List<Leave> findAllByUser_Id(Long userId);

    // Find leaves by user ID and date range
    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.startDate BETWEEN :startDate AND :endDate")
    List<Leave> findLeavesByUserIdAndDateRange(@Param("userId") Long userId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    // Count total leaves for a specific user
    @Query("SELECT COUNT(l) FROM Leave l WHERE l.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    // Find leaves by request status
    List<Leave> findAllByRequestStatus(RequestStatus requestStatus);


}

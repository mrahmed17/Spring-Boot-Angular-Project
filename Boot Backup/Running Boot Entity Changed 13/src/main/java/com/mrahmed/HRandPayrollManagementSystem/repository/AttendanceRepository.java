package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Find all attendances for a specific user
    List<Attendance> findAllByUser_Id(Long userId);

    // Find all attendances for a specific date
    List<Attendance> findAllByDate(LocalDate date);

    // Count total attendances for a specific user
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    // Find all late attendances for a specific user
    List<Attendance> findAllByUser_IdAndLateTrue(Long userId);

    // Find attendances by user within a date range
    @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findAttendancesByUserIdAndDateRange(@Param("userId") Long userId,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);

    // Find today's attendance records for all users
    @Query("SELECT a FROM Attendance a WHERE a.date = :today")
    List<Attendance> findAttendancesForToday(@Param("today") LocalDate today);

    // Find last attendance for a user on a specific date (used for check-out)
    @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date = :date ORDER BY a.clockInTime DESC")
    Optional<Attendance> findLastAttendanceForUser(@Param("userId") Long userId,
                                                   @Param("date") LocalDate date);

    // Count today's attendance records for a user (used to prevent multiple check-ins)
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user.id = :userId AND a.date = :date")
    long countByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    // Find attendances for a specific date range
    @Query("SELECT a FROM Attendance a WHERE a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findAttendancesInRange(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    // Find all late check-ins between two dates based on a specific check-in time
    @Query("SELECT a FROM Attendance a WHERE a.clockInTime > :lateTime AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findLateCheckIns(@Param("lateTime") LocalTime lateTime,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

}

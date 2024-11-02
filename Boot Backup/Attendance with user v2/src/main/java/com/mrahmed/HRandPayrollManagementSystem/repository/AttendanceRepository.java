package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByUserIdAndDate(long userId, LocalDate date);

    long countByUserIdAndDate(long userId, LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findAttendancesInRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date = :today ORDER BY a.clockInTime DESC")
    Optional<Attendance> findLastAttendanceForUser(@Param("userId") long userId, @Param("today") LocalDate today);

    @Query("SELECT u FROM User u WHERE u.id NOT IN " +
            "(SELECT a.user.id FROM Attendance a WHERE a.date = :today)")
    List<User> findUsersWithoutAttendanceForToday(@Param("today") LocalDate today);

    List<Attendance> findAllByUserId(Long id);
}

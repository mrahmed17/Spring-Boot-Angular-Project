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

    @Query("SELECT a FROM Attendance a WHERE LOWER(a.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Attendance> findAttendancesByUserNamePart(@Param("name") String name);

    long countByUserIdAndDate(long userId, LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findAttendancesByUserIdAndDateRange(@Param("userId") long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM Attendance a WHERE a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findAttendancesInRange(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date = CURRENT_DATE")
    Optional<Attendance> findTodayAttendanceByUserId(@Param("userId") long userId);

    @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date = :today ORDER BY a.clockInTime DESC")
    Optional<Attendance> findLastAttendanceForUser(@Param("userId") long userId,
                                                   @Param("today") LocalDate today);

    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT a.user.id FROM Attendance a WHERE a.date = :today)")
    List<User> findUsersWithoutAttendanceForToday(@Param("today") LocalDate today);

    @Query("SELECT a.date, COUNT(a) FROM Attendance a GROUP BY a.date ORDER BY COUNT(a) DESC")
    List<Object[]> findPeakAttendanceDay();

    @Query("SELECT MONTH(a.date), COUNT(a) FROM Attendance a GROUP BY MONTH(a.date) ORDER BY COUNT(a) DESC")
    List<Object[]> findPeakAttendanceMonth();

    @Query("SELECT YEAR(a.date), COUNT(a) FROM Attendance a GROUP BY YEAR(a.date) ORDER BY COUNT(a) DESC")
    List<Object[]> findPeakAttendanceYear();

    @Query("SELECT a.date, COUNT(a) FROM Attendance a WHERE a.date IN (:holidayDates) GROUP BY a.date ORDER BY COUNT(a) ASC")
    List<Object[]> findHolidayAttendance(@Param("holidayDates") List<LocalDate> holidayDates);

    @Query("SELECT a FROM Attendance a WHERE a.clockInTime > :lateTime AND a.date BETWEEN :startDate AND :endDate ORDER BY a.clockInTime DESC")
    List<Attendance> findLateCheckIns(@Param("lateTime") LocalTime lateTime,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT u.id, COUNT(a) FROM Attendance a JOIN a.user u WHERE a.date BETWEEN :startDate AND :endDate GROUP BY u.id ORDER BY COUNT(a) DESC")
    List<Object[]> findRegularEmployeesForShiftPlanning(@Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.date = CURRENT_DATE")
    List<Attendance> findAttendancesForToday();

    @Modifying
    @Transactional
    @Query("DELETE FROM Attendance a WHERE a.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Attendance a WHERE a.date BETWEEN :startDate AND :endDate AND " +
            "((a.clockInTime BETWEEN :morningShiftStart AND :morningShiftEnd AND a.clockOutTime > :morningShiftEnd) OR " +
            "(a.clockInTime BETWEEN :dayShiftStart AND :dayShiftEnd AND a.clockOutTime > :dayShiftEnd))")
    List<Attendance> findOvertimeInRange(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         @Param("morningShiftStart") LocalTime morningShiftStart,
                                         @Param("morningShiftEnd") LocalTime morningShiftEnd,
                                         @Param("dayShiftStart") LocalTime dayShiftStart,
                                         @Param("dayShiftEnd") LocalTime dayShiftEnd);

    @Query("SELECT a.date, u.fullName, a.clockInTime, a.clockOutTime, a.late " +
            "FROM Attendance a JOIN a.user u " +
            "WHERE a.date BETWEEN :startDate AND :endDate AND u.id = :userId " +
            "ORDER BY a.date, a.clockInTime")
    List<Attendance> findAttendanceHistoryForUser(@Param("userId") Long userId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);

    List<Attendance> findAllByUserId(Long userId);

    @Query("SELECT d.departmentName, COUNT(a) FROM Attendance a " +
            "JOIN a.user u " +
            "JOIN u.department d " +
            "GROUP BY d.departmentName " +
            "ORDER BY COUNT(a) DESC")
    List<Attendance> findAttendanceByDepartment();

    @Query("SELECT u FROM User u " +
            "JOIN Attendance a ON a.user.id = u.id " +
            "JOIN Leave l ON a.user.id = l.user.id " +
            "WHERE l.leaveType IS NOT NULL " +
            "GROUP BY u.id " +
            "HAVING COUNT(l) > :threshold")
    List<User> findEmployeesWithHighLeaveRate(@Param("threshold") long threshold);
}

package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.AttendanceRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    private static final LocalTime MORNING_SHIFT_START = LocalTime.of(9, 0);
    private static final LocalTime MORNING_SHIFT_END = LocalTime.of(17, 0);
    private static final LocalTime DAY_SHIFT_START = LocalTime.of(13, 0);
    private static final LocalTime DAY_SHIFT_END = LocalTime.of(21, 0);
    private static final Duration SHIFT_DURATION = Duration.ofHours(8);


    public Attendance checkIn(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (attendanceRepository.countByUserIdAndDate(userId, LocalDate.now()) > 0) {
            throw new RuntimeException("User has already checked in today.");
        }

        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.now());
        attendance.setClockInTime(LocalDateTime.now());
        attendance.setUser(user);
        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(long userId) {
        Attendance attendance = attendanceRepository.findLastAttendanceForUser(userId, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("No check-in found for today's check-out"));

        if (attendance.getClockOutTime() != null) {
            throw new RuntimeException("User has already checked out today.");
        }

        attendance.setClockOutTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getOvertimeForUser(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Attendance> attendances = attendanceRepository.findAttendancesByUserIdAndDateRange(userId, startDateTime, endDateTime);
        return attendances.stream()
                .filter(this::isOvertime)
                .collect(Collectors.toList());
    }

    private boolean isOvertime(Attendance attendance) {
        if (attendance.getClockInTime() == null || attendance.getClockOutTime() == null) {
            return false; // Skip records with missing times
        }
        Duration duration = Duration.between(attendance.getClockInTime(), attendance.getClockOutTime());
        return duration.compareTo(SHIFT_DURATION) > 0; // Overtime if duration exceeds 8 hours
    }


    public List<Attendance> getOvertimeInRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findOvertimeInRange(
                startDate,
                endDate,
                MORNING_SHIFT_START,
                MORNING_SHIFT_END,
                DAY_SHIFT_START,
                DAY_SHIFT_END
        );
    }

    private boolean isWithinMorningShift(LocalTime clockInTime, LocalTime clockOutTime) {
        return !clockInTime.isBefore(MORNING_SHIFT_START) && !clockOutTime.isAfter(MORNING_SHIFT_END);
    }

    private boolean isWithinDayShift(LocalTime clockInTime, LocalTime clockOutTime) {
        return !clockInTime.isBefore(DAY_SHIFT_START) && !clockOutTime.isAfter(DAY_SHIFT_END);
    }

    private boolean isLate(LocalTime checkOutTime, LocalTime shiftEndTime) {
        return checkOutTime.isAfter(shiftEndTime);
    }

    public List<Attendance> getTodayAttendances() {
        return attendanceRepository.findAttendancesForToday();
    }

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance findAttendanceById(long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
    }

    public Map<User, Long> getUsersAttendanceInRange(LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findAttendancesInRange(startDate, endDate);
        return attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getUser, Collectors.counting()));
    }

    public List<Attendance> getAttendanceByUserId(Long id) {
        return attendanceRepository.findAllByUserId(id);
    }

    // findOvertimeInRange
    public List<Attendance> findOvertimeInRange(LocalDate startDate, LocalDate endDate, LocalTime morningShiftStart, LocalTime morningShiftEnd, LocalTime dayShiftStart, LocalTime dayShiftEnd) {
        return attendanceRepository.findOvertimeInRange(startDate, endDate, morningShiftStart, morningShiftEnd, dayShiftStart, dayShiftEnd);
    }


    public List<Attendance> getAttendanceHistoryForUser(long userId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findAttendanceHistoryForUser(userId, startDate, endDate);
    }

    public List<User> findUsersWithoutAttendanceToday() {
        return attendanceRepository.findUsersWithoutAttendanceForToday(LocalDate.now());
    }

    public List<Object[]> getPeakAttendanceDay() {
        return attendanceRepository.findPeakAttendanceDay();
    }

    public List<Object[]> getPeakAttendanceMonth() {
        return attendanceRepository.findPeakAttendanceMonth();
    }

    public List<Object[]> getPeakAttendanceYear() {
        return attendanceRepository.findPeakAttendanceYear();
    }

    public List<Object[]> getHolidayAttendance(List<LocalDate> holidayDates) {
        return attendanceRepository.findHolidayAttendance(holidayDates);
    }

    public List<Attendance> getLateCheckIns(LocalTime lateTime, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findLateCheckIns(lateTime, startDate, endDate);
    }

    public List<Object[]> getRegularEmployeesForShiftPlanning(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findRegularEmployeesForShiftPlanning(startDate, endDate);
    }

    public List<Attendance> getAttendancesByUserNamePart(String name) {
        return attendanceRepository.findAttendancesByUserNamePart(name);
    }

    public Optional<Attendance> getTodayAttendanceByUserId(long userId) {
        return attendanceRepository.findTodayAttendanceByUserId(userId);
    }

    // Get department-wise attendance trends
    public List<Attendance> getAttendanceByDepartment() {
        return attendanceRepository.findAttendanceByDepartment();
    }

    // Get employees with a high leave rate
    public List<User> getEmployeesWithHighLeaveRate(long threshold) {
        return attendanceRepository.findEmployeesWithHighLeaveRate(threshold);
    }


}
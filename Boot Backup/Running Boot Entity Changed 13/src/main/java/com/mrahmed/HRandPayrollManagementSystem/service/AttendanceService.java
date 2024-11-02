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
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    // Check-in a user for attendance
    public Attendance checkIn(long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            // Prevent multiple check-ins on the same day
            if (attendanceRepository.countByUserIdAndDate(userId, LocalDate.now()) > 0) {
                throw new RuntimeException("User has already checked in today.");
            }

            Attendance attendance = new Attendance();
            attendance.setDate(LocalDate.now());
            attendance.setClockInTime(LocalDateTime.now());
            attendance.setUser(user);
            return attendanceRepository.save(attendance);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while checking in: " + e.getMessage(), e);
        }
    }

    // Check-out a user for attendance
    public Attendance checkOut(long userId) {
        try {
            Attendance attendance = attendanceRepository.findLastAttendanceForUser(userId, LocalDate.now())
                    .orElseThrow(() -> new RuntimeException("No check-in found for today's check-out"));

            // Prevent multiple check-outs
            if (attendance.getClockOutTime() != null) {
                throw new RuntimeException("User has already checked out today.");
            }

            attendance.setClockOutTime(LocalDateTime.now());
            return attendanceRepository.save(attendance);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while checking out: " + e.getMessage(), e);
        }
    }

    // Get overtime for a user in a date range
    public List<Attendance> getOvertimeForUser(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findAttendancesByUserIdAndDateRange(userId, startDate, endDate);
        return attendances.stream()
                .filter(this::isOvertime)
                .collect(Collectors.toList());
    }

    // Check if the attendance record contains overtime
    private boolean isOvertime(Attendance attendance) {
        if (attendance.getClockInTime() == null || attendance.getClockOutTime() == null) {
            return false; // Skip records with missing times
        }
        Duration duration = Duration.between(attendance.getClockInTime(), attendance.getClockOutTime());
        long hours = duration.toHours();
        return hours > 8; // Assuming a workday is 8 hours
    }

    // Get all attendances for today
    public List<Attendance> getTodayAttendances() {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findAttendancesForToday(today);
    }

    // Get all attendances
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    // Find attendance by ID
    public Attendance findAttendanceById(long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
    }

    // Get all attendances for a user by ID
    public List<Attendance> getAttendanceByUserId(Long userId) {
        return attendanceRepository.findAllByUser_Id(userId);
    }

    // Get late attendances for a specific user
    public List<Attendance> getLateAttendancesForUser(Long userId) {
        return attendanceRepository.findAllByUser_IdAndLateTrue(userId);
    }

    // Get users' attendance count within a date range
    public Map<User, Long> getUsersAttendanceInRange(LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findAttendancesInRange(startDate, endDate);
        return attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getUser, Collectors.counting()));
    }


    // Find all attendances for a specific date
    public List<Attendance> getAttendancesByDate(LocalDate date) {
        return attendanceRepository.findAllByDate(date);
    }

    // Count total attendances for a specific user
    public long countAttendancesByUserId(Long userId) {
        return attendanceRepository.countByUserId(userId);
    }

    // Find all late check-ins between two dates based on a specific check-in time
    public List<Attendance> getLateCheckIns(LocalTime lateTime, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findLateCheckIns(lateTime, startDate, endDate);
    }



}

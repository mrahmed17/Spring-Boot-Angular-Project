package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.AttendanceRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

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

    public List<User> findUsersWithoutAttendanceToday() {
        return attendanceRepository.findUsersWithoutAttendanceForToday(LocalDate.now());
    }


}
package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.AttendanceRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;


    public void saveAttendance(Attendance attend) {
        attendanceRepository.save(attend);
    }


    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendancesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return attendanceRepository.findByUserId(userId);
    }


    public int getAttendanceCountByUserAndMonth(Long userId, Month month) {
        try {
            int count = attendanceRepository.findAttendanceByUserAndMonth(userId, month);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching attendance count for user " + userId + " and month " + month, e);
        }
    }


    public void deleteAttendanceById(long id) {
        attendanceRepository.deleteById(id);
    }


    public Attendance findAttendanceById(long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
    }
}

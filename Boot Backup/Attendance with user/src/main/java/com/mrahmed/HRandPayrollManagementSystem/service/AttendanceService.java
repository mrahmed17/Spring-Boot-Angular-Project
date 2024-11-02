package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.AttendanceRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {


    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    public Attendance saveAttendance(Attendance attendance) {
        User user = userRepository.findById(attendance.getUser().getId()).orElseThrow(()-> new RuntimeException("User not found"));
        attendance.setUser(user);
        attendanceRepository.save(attendance);
        return attendance;
    }

//    public void saveAttendance(Attendance attendance) {
//        attendanceRepository.save(attendance);
//    }

    public List<Attendance> getAllAttendances(){
        return attendanceRepository.findAll();
    }

//    public void deleteAttendanceById(long id) {
//        attendanceRepository.deleteById(id);
//    }

    public Attendance findAttendanceById(long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
    }


    public Map<User, Long> getUsersAttendanceInRange(LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findAttendancesInRange(startDate, endDate);

        return attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getUser, Collectors.counting()));
    }



//    public Map<User, Long> getUsersAttendanceInRange(LocalDate startDate, LocalDate endDate) {
//        List<Object[]> results = attendanceRepository.findUsersWithAttendanceInRange(startDate, endDate);
//        Map<User, Long> userAttendanceMap = new HashMap<>();
//
//        for (Object[] result : results) {
//            User user = (User) result[0];
//            Long attendanceCount = (Long) result[1];
//            userAttendanceMap.put(user, attendanceCount);
//        }
//
//        return userAttendanceMap;
//    }


}

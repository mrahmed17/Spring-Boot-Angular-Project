package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.AttendanceService;
import com.mrahmed.HRandPayrollManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceRestController {
    @Autowired
    private AttendanceService attendanceService;

    // Check-in endpoint
    @PostMapping("/check-in/{userId}")
    public ResponseEntity<Attendance> checkIn(@PathVariable long userId) {
        try {
            Attendance attendance = attendanceService.checkIn(userId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Check-out endpoint
    @PostMapping("/check-out/{userId}")
    public ResponseEntity<Attendance> checkOut(@PathVariable long userId) {
        try {
            Attendance attendance = attendanceService.checkOut(userId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get all attendances for today
    @GetMapping("/today")
    public ResponseEntity<List<Attendance>> getTodayAttendances() {
        List<Attendance> attendances = attendanceService.getTodayAttendances();
        return ResponseEntity.ok(attendances);
    }

    // Get all attendances
    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    // Get attendances by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Attendance>> getAttendancesByDate(@PathVariable LocalDate date) {
        List<Attendance> attendances = attendanceService.getAttendancesByDate(date);
        return ResponseEntity.ok(attendances);
    }

    // Get attendance count for a user
    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Long> countAttendancesByUserId(@PathVariable Long userId) {
        long count = attendanceService.countAttendancesByUserId(userId);
        return ResponseEntity.ok(count);
    }

    // Get late check-ins within date range
    @GetMapping("/late-checkins")
    public ResponseEntity<List<Attendance>> getLateCheckIns(
            @RequestParam("lateTime") String lateTime,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        LocalTime parsedLateTime = LocalTime.parse(lateTime);
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        List<Attendance> attendances = attendanceService.getLateCheckIns(parsedLateTime, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(attendances);
    }

    // Get attendance for user by ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Attendance>> getAttendanceByUserId(@PathVariable Long userId) {
        List<Attendance> attendances = attendanceService.getAttendanceByUserId(userId);
        return ResponseEntity.ok(attendances);
    }

    // Get late attendances for a user
    @GetMapping("/user/{userId}/late")
    public ResponseEntity<List<Attendance>> getLateAttendancesForUser(@PathVariable Long userId) {
        List<Attendance> attendances = attendanceService.getLateAttendancesForUser(userId);
        return ResponseEntity.ok(attendances);
    }

    // Get attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> findAttendanceById(@PathVariable long id) {
        Attendance attendance = attendanceService.findAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }


}

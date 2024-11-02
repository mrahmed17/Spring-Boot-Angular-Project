package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.AttendanceService;
import com.mrahmed.HRandPayrollManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceRestController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        if (attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/alluser")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PostMapping("/checkin")
    public ResponseEntity<Attendance> checkIn(@RequestBody Map<String, Object> request) {
        long userId = Long.parseLong(request.get("userId").toString());
        Attendance attendance = attendanceService.checkIn(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
    }

    @PutMapping("/checkout")
    public ResponseEntity<Attendance> checkOut(@RequestBody Map<String, Object> request) {
        long userId = Long.parseLong(request.get("userId").toString());
        Attendance attendance = attendanceService.checkOut(userId);
        return ResponseEntity.ok(attendance);
    }


    @GetMapping("/attendance/{id}")
    public ResponseEntity<Attendance> findAttendanceById(@PathVariable("id") long id) {
        Attendance attendance = attendanceService.findAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/user/{id}/attendances")
    public ResponseEntity<List<Attendance>> getAttendancesByUserId(@PathVariable ("id")Long id) {
        List<Attendance> attendances = attendanceService.getAttendanceByUserId(id);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/attendancerange")
    public ResponseEntity<Map<User, Long>> getUsersWithAttendanceInRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        Map<User, Long> userAttendance = attendanceService.getUsersAttendanceInRange(startDate, endDate);
        return ResponseEntity.ok(userAttendance);
    }
}

package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.AttendanceService;
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

    @GetMapping("/")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        if (attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(attendances);
    }

//    @GetMapping("/")
//    public ResponseEntity<List<Attendance>> getAllAttendances() {
//        return ResponseEntity.ok(attendanceService.getAllAttendances());
//    }


    @PostMapping("/save")
    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
        Attendance savedAttendance = attendanceService.saveAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttendance);
    }


//    @PostMapping("/save")
//    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
//        attendanceService.saveAttendance(attendance);
//        return ResponseEntity.ok(attendance);
//    }


    @GetMapping("/view/{id}")
    public ResponseEntity<Attendance> findAttendanceById(@PathVariable("id") long id) {
        Attendance attendance = attendanceService.findAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }

//    @GetMapping("/view/{id}")
//    public ResponseEntity<Attendance> findAttendanceById(@PathVariable Long id) {
//        return ResponseEntity.ok(attendanceService.findAttendanceById(id));
//    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteAttendance(@PathVariable("id") long id) {
//        attendanceService.deleteAttendanceById(id);
//        return ResponseEntity.noContent().build();
//    }


    @GetMapping("/attendance-with-range")
    public ResponseEntity<Map<User, Long>> getUsersWithAttendanceInRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        Map<User, Long> userAttendance = attendanceService.getUsersAttendanceInRange(startDate, endDate);
        return ResponseEntity.ok(userAttendance);
    }

}

package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/")
    public ResponseEntity<?> getAllAttendances() {
        try {
            List<Attendance> attendances = attendanceService.getAllAttendances();
            if (attendances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No attendance records found.");
            }
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching attendance data.");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAttendance(@RequestBody Attendance attendance) {
        try {
            attendanceService.saveAttendance(attendance);
            return ResponseEntity.status(HttpStatus.CREATED).body("Attendance saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving attendance.");
        }
    }


    @GetMapping("/userattend/{userid}")
    public ResponseEntity<?> getUserAttendances(@PathVariable("userid") long userid) {
        try {
            List<Attendance> attendances = attendanceService.getAttendancesByUserId(userid);
            if (attendances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No attendances found for the user.");
            }
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user's attendance data.");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable("id") long id) {
        try {
            attendanceService.deleteAttendanceById(id);
            return ResponseEntity.ok("Attendance deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting attendance.");
        }
    }

    @GetMapping("/present")
    public ResponseEntity<?> getTotalDays(@RequestParam("userId") Long userId, @RequestParam("month") int month) {
        try {
            // Convert int to Month enum
            Month monthEnum = Month.of(month);

            int totalDays = attendanceService.getAttendanceCountByUserAndMonth(userId, monthEnum);
            return ResponseEntity.ok("Total attendance days for user " + userId + " in month " + monthEnum + ": " + totalDays);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid month value provided.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching attendance count.");
        }
    }



    @GetMapping("/view/{id}")
    public ResponseEntity<?> findAttendanceById(@PathVariable("id") long id) {
        try {
            Attendance attendance = attendanceService.findAttendanceById(id);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching attendance by ID.");
        }
    }


}

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

    @GetMapping("/overtime/{userId}")
    public ResponseEntity<List<Attendance>> getOvertimeByUser(
            @PathVariable Long userId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Attendance> overtimeRecords = attendanceService.getOvertimeForUser(userId, startDate, endDate);

        if (overtimeRecords.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no overtime found
        }

        return ResponseEntity.ok(overtimeRecords); // Return the list of overtime records
    }

    @GetMapping("/today")
    public ResponseEntity<List<Attendance>> getTodayAttendance() {
        List<Attendance> attendances = attendanceService.getTodayAttendances();
        if (attendances.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        if (attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/allUsers")
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


    @GetMapping("/find/{id}")
    public ResponseEntity<Attendance> findAttendanceById(@PathVariable("id") long id) {
        Attendance attendance = attendanceService.findAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/user/{id}/attendances")
    public ResponseEntity<List<Attendance>> getAttendancesByUserId(@PathVariable Long id) {
        List<Attendance> attendances = attendanceService.getAttendanceByUserId(id);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/attendanceRange")
    public ResponseEntity<Map<User, Long>> getUsersWithAttendanceInRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        Map<User, Long> userAttendance = attendanceService.getUsersAttendanceInRange(startDate, endDate);
        if (userAttendance == null || userAttendance.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(userAttendance);
    }


//    @GetMapping("/attendanceRange")
//    public ResponseEntity<Map<User, Long>> getUsersWithAttendanceInRange(
//            @RequestParam("startDate") LocalDate startDate,
//            @RequestParam("endDate") LocalDate endDate) {
//        Map<User, Long> userAttendance = attendanceService.getUsersAttendanceInRange(startDate, endDate);
//        return ResponseEntity.ok(userAttendance);
//    }

    @GetMapping("/peakAttendanceDay")
    public ResponseEntity<List<Object[]>> getPeakAttendanceDay() {
        List<Object[]> peakAttendanceDay = attendanceService.getPeakAttendanceDay();
        if (peakAttendanceDay.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(peakAttendanceDay);
    }


    @GetMapping("/peakAttendanceMonth")
    public ResponseEntity<List<Object[]>> getPeakAttendanceMonth() {
        List<Object[]> peakAttendanceMonth = attendanceService.getPeakAttendanceMonth();
        if (peakAttendanceMonth.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(peakAttendanceMonth);
    }

    @GetMapping("/peakAttendanceYear")
    public ResponseEntity<List<Object[]>> getPeakAttendanceYear() {
        List<Object[]> peakAttendanceYear = attendanceService.getPeakAttendanceYear();
        if (peakAttendanceYear.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(peakAttendanceYear);
    }


    @PostMapping("/holidayAttendance")
    public ResponseEntity<List<Object[]>> getHolidayAttendance(@RequestBody List<LocalDate> holidayDates) {
        List<Object[]> holidayAttendance = attendanceService.getHolidayAttendance(holidayDates);
        if (holidayAttendance.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(holidayAttendance);
    }

    @GetMapping("/lateCheckIns")
    public ResponseEntity<List<Attendance>> getLateCheckIns(
            @RequestParam("lateTime") String lateTime,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<Attendance> lateCheckIns = attendanceService.getLateCheckIns(lateTime, startDate, endDate);
        if (lateCheckIns.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lateCheckIns);
    }

    @GetMapping("/shiftPlanning")
    public ResponseEntity<List<Object[]>> getRegularEmployeesForShiftPlanning(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<Object[]> regularEmployees = attendanceService.getRegularEmployeesForShiftPlanning(startDate, endDate);
        if (regularEmployees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(regularEmployees);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<Attendance>> getAttendancesByUserNamePart(@RequestParam("name") String name) {
        List<Attendance> attendances = attendanceService.getAttendancesByUserNamePart(name);
        if (attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/roleAttendance")
    public ResponseEntity<List<Attendance>> getAttendanceByRoleAndDateRange(
            @RequestParam("role") String role,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<Attendance> attendances = attendanceService.getAttendanceByRoleAndDateRange(role, startDate, endDate);
        if (attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(attendances);
    }


    @GetMapping("/todayAttendance/{userId}")
    public ResponseEntity<List<Attendance>> getTodayAttendanceByUserId(@PathVariable("userId") long userId) {
        List<Attendance> todayAttendance = attendanceService.getTodayAttendanceByUserId(userId);
        if (todayAttendance.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(todayAttendance);
    }



}

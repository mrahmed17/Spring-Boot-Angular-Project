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

    @Autowired
    private UserService userService;


    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@RequestBody Map<String, Object> request) {
        try {
            long userId = Long.parseLong(request.get("userId").toString());
            Attendance attendance = attendanceService.checkIn(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid user ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while checking in");
        }
    }

    @PutMapping("/checkout")
    public ResponseEntity<?> checkOut(@RequestBody Map<String, Object> request) {
        try {
            long userId = Long.parseLong(request.get("userId").toString());
            Attendance attendance = attendanceService.checkOut(userId);
            return ResponseEntity.ok(attendance);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid user ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while checking out");
        }
    }


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


    @GetMapping("/all")
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
            @RequestParam("lateTime") LocalTime lateTime,
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


    @GetMapping("/todayAttendance/{userId}")
    public ResponseEntity<Optional<Attendance>> getTodayAttendanceByUserId(@PathVariable("userId") long userId) {
        Optional<Attendance> todayAttendance = attendanceService.getTodayAttendanceByUserId(userId);
        if (todayAttendance.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(todayAttendance);
    }

      @GetMapping("/attendance-by-department")
    public ResponseEntity<List<Object[]>> getAttendanceByDepartment() {
        List<Object[]> attendanceTrends = attendanceService.getAttendanceByDepartment();
        return ResponseEntity.ok(attendanceTrends);
    }

    @GetMapping("/high-leave-rate")
    public ResponseEntity<List<User>> getEmployeesWithHighLeaveRate(@RequestParam long threshold) {
        List<User> employees = attendanceService.getEmployeesWithHighLeaveRate(threshold);
        return ResponseEntity.ok(employees);
    }

     @GetMapping("/overtime")
    public ResponseEntity<List<Attendance>> findOvertimeInRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam LocalTime morningShiftStart,
            @RequestParam LocalTime morningShiftEnd,
            @RequestParam LocalTime dayShiftStart,
            @RequestParam LocalTime dayShiftEnd) {
        List<Attendance> overtimeRecords = attendanceService.findOvertimeInRange(startDate, endDate, morningShiftStart, morningShiftEnd, dayShiftStart, dayShiftEnd);
        return ResponseEntity.ok(overtimeRecords);
    }

    @GetMapping("/attendance-history")
    public ResponseEntity<List<Attendance>> getAttendanceHistoryForUser(
            @RequestParam long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Attendance> attendanceHistory = attendanceService.getAttendanceHistoryForUser(userId, startDate, endDate);
        return ResponseEntity.ok(attendanceHistory);
    }

     @GetMapping("/users-without-attendance")
    public ResponseEntity<List<User>> findUsersWithoutAttendanceToday() {
        List<User> usersWithoutAttendance = attendanceService.findUsersWithoutAttendanceToday();
        return ResponseEntity.ok(usersWithoutAttendance);
    }


}

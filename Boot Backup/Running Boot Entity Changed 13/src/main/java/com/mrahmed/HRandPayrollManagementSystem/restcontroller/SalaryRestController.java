package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Salary;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salaries")
@CrossOrigin("*")
public class SalaryRestController {


    @Autowired
    private SalaryService salaryService;

    @PostMapping("/create")
    public ResponseEntity<Salary> createSalary(@RequestBody Salary salary) {
        Salary createdSalary = salaryService.createSalary(salary);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSalary);
    }

    @PutMapping("/update/{salaryId}")
    public ResponseEntity<Salary> updateSalary(@PathVariable Long salaryId, @RequestBody Salary updatedSalary) {
        Salary salary = salaryService.updateSalary(salaryId, updatedSalary);
        return ResponseEntity.ok(salary);
    }

    @DeleteMapping("/delete/{salaryId}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long salaryId) {
        salaryService.deleteSalary(salaryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{salaryId}")
    public ResponseEntity<Salary> getSalaryById(@PathVariable Long salaryId) {
        Optional<Salary> salary = salaryService.getSalaryById(salaryId);
        return salary.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //getAllSalaries
    @GetMapping("/all")
    public ResponseEntity<List<Salary>> getAllSalaries() {
        List<Salary> salaries = salaryService.getAllSalaries();
        return ResponseEntity.ok(salaries);
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Salary>> getSalariesByUserId(@PathVariable Long userId) {
        List<Salary> salaries = salaryService.getSalariesByUserId(userId);
        return ResponseEntity.ok(salaries);
    }


    @GetMapping("/range")
    public ResponseEntity<List<Salary>> getSalariesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Salary> salaries = salaryService.getSalariesByDateRange(startDate, endDate);
        return ResponseEntity.ok(salaries);
    }


    @GetMapping("/user/{userId}/calculate")
    public ResponseEntity<Double> calculateTotalSalary(@PathVariable Long userId,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        double totalSalary = salaryService.calculateTotalSalary(userId, startDate, endDate);
        return ResponseEntity.ok(totalSalary);
    }


    @GetMapping("/user/{userId}/overtime")
    public ResponseEntity<Double> getOvertimeSalary(@PathVariable Long userId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        double overtimeSalary = salaryService.calculateOvertimeSalary(userId, startDate, endDate);
        return ResponseEntity.ok(overtimeSalary);
    }


    @GetMapping("/user/{userId}/overtime-hours")
    public ResponseEntity<Double> getTotalOvertimeHours(@PathVariable Long userId,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        double totalOvertimeHours = salaryService.getTotalOvertimeHours(userId, startDate, endDate);
        return ResponseEntity.ok(totalOvertimeHours);
    }


    // Endpoint to filter salaries by full name
    @GetMapping("/filter/fullname")
    public ResponseEntity<List<Salary>> getSalariesByFullName(@RequestParam String name) {
        List<Salary> salaries = salaryService.findByFullName(name);
        return new ResponseEntity<>(salaries, HttpStatus.OK);
    }

    // Endpoint to get salary by email
    @GetMapping("/filter/email")
    public ResponseEntity<Salary> getSalaryByEmail(@RequestParam String email) {
        Optional<Salary> salary = salaryService.findByEmail(email);
        return salary.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}

package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.entity.Salary;
import com.mrahmed.HRandPayrollManagementSystem.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        Salary createdSalary = salaryService.saveSalary(salary);
        return ResponseEntity.ok(createdSalary);
    }

    @PutMapping("/update/{salaryId}")
    public ResponseEntity<Salary> updateSalary(@PathVariable Long salaryId,
                                               @RequestBody Salary salary) {
        Salary updatedSalary = salaryService.updateSalary(salaryId, salary);
        return ResponseEntity.ok(updatedSalary);
    }


    @DeleteMapping("/delete/{salaryId}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long salaryId) {
        salaryService.deleteSalary(salaryId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/find/{salaryId}")
    public ResponseEntity<Salary> getSalaryById(@PathVariable Long salaryId) {
        Salary salary = salaryService.getSalaryById(salaryId);
        return ResponseEntity.ok(salary);
    }


    @GetMapping("/user/{userId}/year/{year}")
    public ResponseEntity<List<Salary>> getSalariesByUserAndYear(@PathVariable Long userId,
                                                                 @PathVariable int year) {
        List<Salary> salaries = salaryService.getSalariesByUserAndYear(userId, year);
        return ResponseEntity.ok(salaries);
    }


    @GetMapping("/user/{userId}/year/{year}/month/{month}")
    public ResponseEntity<List<Salary>> getSalariesByUserYearAndMonth(@PathVariable Long userId,
                                                                      @PathVariable int year,
                                                                      @PathVariable Month month) {
        List<Salary> salaries = salaryService.getSalariesByUserYearAndMonth(userId, year, month);
        return ResponseEntity.ok(salaries);
    }


    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<List<Salary>> getLatestSalaryByUser(@PathVariable Long userId) {
        List<Salary> latestSalary = salaryService.getSalariesByUserAndYear(userId, LocalDateTime.now().getYear());
        return ResponseEntity.ok(latestSalary);
    }


    @GetMapping("/user/{userId}/year/{year}/total")
    public ResponseEntity<BigDecimal> getTotalSalaryByUserAndYear(@PathVariable Long userId,
                                                                  @PathVariable int year) {
        BigDecimal totalSalary = salaryService.getTotalSalaryByUserAndYear(userId, year);
        return ResponseEntity.ok(totalSalary);
    }


    @GetMapping("/range")
    public ResponseEntity<List<Salary>> getSalariesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Salary> salaries = salaryService.getSalariesByDateRange(startDate, endDate);
        return ResponseEntity.ok(salaries);
    }


    @GetMapping("/user/{userId}/calculate")
    public ResponseEntity<BigDecimal> calculateTotalSalary(@PathVariable Long userId,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal totalSalary = salaryService.calculateTotalSalary(userId, startDate, endDate);
        return ResponseEntity.ok(totalSalary);
    }


    @GetMapping("/user/{userId}/overtime")
    public ResponseEntity<BigDecimal> getOvertimeSalary(@PathVariable Long userId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal overtimeSalary = salaryService.calculateOvertimeSalary(userId, startDate, endDate);
        return ResponseEntity.ok(overtimeSalary);
    }


    @GetMapping("/user/{userId}/overtime-hours")
    public ResponseEntity<BigDecimal> getTotalOvertimeHours(@PathVariable Long userId,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal totalOvertimeHours = salaryService.getTotalOvertimeHours(userId, startDate, endDate);
        return ResponseEntity.ok(totalOvertimeHours);
    }


}

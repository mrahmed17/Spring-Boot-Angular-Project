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


    /**
     * Create a new salary record.
     *
     * @param salary Salary entity
     * @return Created Salary entity
     */
    @PostMapping("/create")
    public ResponseEntity<Salary> createSalary(@RequestBody Salary salary) {
        Salary createdSalary = salaryService.saveSalary(salary);
        return ResponseEntity.ok(createdSalary);
    }

    /**
     * Update an existing salary record.
     *
     * @param salaryId Salary ID
     * @param salary   Updated Salary entity
     * @return Updated Salary entity
     */
    @PutMapping("/update/{salaryId}")
    public ResponseEntity<Salary> updateSalary(@PathVariable Long salaryId,
                                               @RequestBody Salary salary) {
        Salary updatedSalary = salaryService.updateSalary(salaryId, salary);
        return ResponseEntity.ok(updatedSalary);
    }

    /**
     * Delete a salary record by ID.
     *
     * @param salaryId Salary ID
     * @return Response status
     */
    @DeleteMapping("/delete/{salaryId}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long salaryId) {
        salaryService.deleteSalary(salaryId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a salary record by ID.
     *
     * @param salaryId Salary ID
     * @return Salary entity
     */
    @GetMapping("/find/{salaryId}")
    public ResponseEntity<Salary> getSalaryById(@PathVariable Long salaryId) {
        Salary salary = salaryService.getSalaryById(salaryId);
        return ResponseEntity.ok(salary);
    }



    /**
     * Get all salaries for a user by year.
     *
     * @param userId User ID
     * @param year   Year
     * @return List of salaries
     */
    @GetMapping("/user/{userId}/year/{year}")
    public ResponseEntity<List<Salary>> getSalariesByUserAndYear(@PathVariable Long userId,
                                                                 @PathVariable int year) {
        List<Salary> salaries = salaryService.getSalariesByUserAndYear(userId, year);
        return ResponseEntity.ok(salaries);
    }

    /**
     * Get salaries for a user by year and month.
     *
     * @param userId User ID
     * @param year   Year
     * @param month  Payroll Month
     * @return List of salaries
     */
    @GetMapping("/user/{userId}/year/{year}/month/{month}")
    public ResponseEntity<List<Salary>> getSalariesByUserYearAndMonth(@PathVariable Long userId,
                                                                      @PathVariable int year,
                                                                      @PathVariable Month month) {
        List<Salary> salaries = salaryService.getSalariesByUserYearAndMonth(userId, year, month);
        return ResponseEntity.ok(salaries);
    }

    /**
     * Get the latest salary record for a user.
     *
     * @param userId User ID
     * @return Latest salary record
     */
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<List<Salary>> getLatestSalaryByUser(@PathVariable Long userId) {
        List<Salary> latestSalary = salaryService.getSalariesByUserAndYear(userId, LocalDateTime.now().getYear());
        return ResponseEntity.ok(latestSalary);
    }

    /**
     * Get total salary for a user in a year.
     *
     * @param userId User ID
     * @param year   Year
     * @return Total salary
     */
    @GetMapping("/user/{userId}/year/{year}/total")
    public ResponseEntity<BigDecimal> getTotalSalaryByUserAndYear(@PathVariable Long userId,
                                                                  @PathVariable int year) {
        BigDecimal totalSalary = salaryService.getTotalSalaryByUserAndYear(userId, year);
        return ResponseEntity.ok(totalSalary);
    }

    /**
     * Get salaries within a date range.
     *
     * @param startDate Start date
     * @param endDate   End date
     * @return List of salaries
     */
    @GetMapping("/range")
    public ResponseEntity<List<Salary>> getSalariesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Salary> salaries = salaryService.getSalariesByDateRange(startDate, endDate);
        return ResponseEntity.ok(salaries);
    }

    /**
     * Calculate total salary for a user in a given period.
     *
     * @param userId    User ID
     * @param startDate Start date
     * @param endDate   End date
     * @return Total salary
     */
    @GetMapping("/user/{userId}/calculate")
    public ResponseEntity<BigDecimal> calculateTotalSalary(@PathVariable Long userId,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal totalSalary = salaryService.calculateTotalSalary(userId, startDate, endDate);
        return ResponseEntity.ok(totalSalary);
    }

    /**
     * Get total overtime salary for a user within a specific date range.
     *
     * @param userId    User ID
     * @param startDate Start date
     * @param endDate   End date
     * @return Total overtime salary
     */
    @GetMapping("/user/{userId}/overtime")
    public ResponseEntity<BigDecimal> getOvertimeSalary(@PathVariable Long userId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal overtimeSalary = salaryService.calculateOvertimeSalary(userId, startDate, endDate);
        return ResponseEntity.ok(overtimeSalary);
    }

    /**
     * Get total overtime hours for a user in a specific period.
     *
     * @param userId    User ID
     * @param startDate Start date
     * @param endDate   End date
     * @return Total overtime hours
     */
    @GetMapping("/user/{userId}/overtime-hours")
    public ResponseEntity<BigDecimal> getTotalOvertimeHours(@PathVariable Long userId,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal totalOvertimeHours = salaryService.getTotalOvertimeHours(userId, startDate, endDate);
        return ResponseEntity.ok(totalOvertimeHours);
    }


}

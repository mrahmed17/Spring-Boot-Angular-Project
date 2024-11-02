package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.*;
import com.mrahmed.HRandPayrollManagementSystem.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UserService userService;


    /**
     * Save a new salary record.
     *
     * @param salary Salary entity
     * @return Saved Salary entity
     */
    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    /**
     * Update an existing salary record.
     *
     * @param salaryId Salary ID
     * @param salary   Salary entity with updated information
     * @return Updated Salary entity
     */
    public Salary updateSalary(Long salaryId, Salary salary) {
        if (!salaryRepository.existsById(salaryId)) {
            throw new RuntimeException("Salary record not found.");
        }
        salary.setId(salaryId);
        return salaryRepository.save(salary);
    }

    /**
     * Delete a salary record by ID.
     *
     * @param salaryId Salary ID
     */
    public void deleteSalary(Long salaryId) {
        if (!salaryRepository.existsById(salaryId)) {
            throw new RuntimeException("Salary record not found.");
        }
        salaryRepository.deleteById(salaryId);
    }

    /**
     * Get a salary record by ID.
     *
     * @param salaryId Salary ID
     * @return Salary entity
     */
    public Salary getSalaryById(Long salaryId) {
        return salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary record not found."));
    }


    /**
     * Calculate overtime salary for a specific user between a date range
     *
     * @param userId     User ID
     * @param startDate  Start Date
     * @param endDate    End Date
     * @return Overtime salary for the given period
     */
    public BigDecimal calculateOvertimeSalary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Retrieve total overtime hours for the user in the given range
        BigDecimal totalOvertimeHours = salaryRepository.getTotalOvertimeHoursByUserAndDateRange(userId, startDate, endDate);
        if (totalOvertimeHours == null) {
            totalOvertimeHours = BigDecimal.ZERO;
        }

        // Get user and their basic salary
        User user = userService.findUserById(userId);
        BigDecimal basicSalary = user.getBasicSalary();

        // Calculate overtime rate: basic salary / (4 weeks * 5 days * 8 hours)
        BigDecimal hourlyRate = basicSalary.divide(BigDecimal.valueOf(4 * 5 * 8), BigDecimal.ROUND_HALF_UP);

        // Calculate overtime salary = overtime hours * hourly rate
        BigDecimal overtimeSalary = totalOvertimeHours.multiply(hourlyRate);

        return overtimeSalary;
    }

    /**
     * Calculate the total salary for a user for a specific period.
     *
     * @param userId     User ID
     * @param startDate  Start date of the salary calculation period
     * @param endDate    End date of the salary calculation period
     * @return Total salary, including bonuses, allowances, overtime, and deductions.
     */
    @Transactional
    public BigDecimal calculateTotalSalary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Retrieve the user's latest salary record
        List<Salary> latestSalaries = salaryRepository.findLatestSalaryByUser(userId);
        if (latestSalaries.isEmpty()) {
            throw new RuntimeException("No salary record found for the user.");
        }

        Salary latestSalary = latestSalaries.get(0);

        // Get overtime salary for the user in the date range
        BigDecimal overtimeSalary = calculateOvertimeSalary(userId, startDate, endDate);

        // Calculate total bonuses
        BigDecimal totalBonuses = latestSalary.getBonuses().stream()
                .map(Bonus::getBonusAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate allowances
        BigDecimal totalAllowances = latestSalary.getTransportAllowance()
                .add(latestSalary.getTelephoneSubsidy())
                .add(latestSalary.getUtilityAllowance())
                .add(latestSalary.getDomesticAllowance())
                .add(latestSalary.getLunchAllowance());

        // Deductions
        BigDecimal totalDeductions = latestSalary.getMedicare()
                .add(latestSalary.getProvidentFund())
                .add(latestSalary.getInsurance())
                .add(latestSalary.getTax());

        // Calculate total net salary
        BigDecimal totalSalary = latestSalary.getNetSalary()
                .add(overtimeSalary)
                .add(totalBonuses)
                .add(totalAllowances)
                .subtract(totalDeductions);

        return totalSalary;
    }

    /**
     * Get salary records by user and year
     *
     * @param userId User ID
     * @param year   Year
     * @return List of Salary records for the user
     */
    public List<Salary> getSalariesByUserAndYear(Long userId, int year) {
        return salaryRepository.findSalariesByUserAndYear(userId, year);
    }

    /**
     * Get total overtime hours for a user within a date range.
     *
     * @param userId    User ID
     * @param startDate Start Date
     * @param endDate   End Date
     * @return Total overtime hours
     */
    public BigDecimal getTotalOvertimeHours(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return salaryRepository.getTotalOvertimeHoursByUserAndDateRange(userId, startDate, endDate);
    }

    /**
     * Get salary records by user, year, and month
     *
     * @param userId       User ID
     * @param year         Year
     * @param payrollMonth Month
     * @return List of salary records for the user in the specific month and year
     */
    public List<Salary> getSalariesByUserYearAndMonth(Long userId, int year, Month payrollMonth) {
        return salaryRepository.findSalariesByUserYearAndMonth(userId, year, payrollMonth);
    }

    /**
     * Get salaries within a specific date range
     *
     * @param startDate Start date
     * @param endDate   End date
     * @return List of salary records in the specified date range
     */
    public List<Salary> getSalariesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return salaryRepository.findSalariesByDateRange(startDate, endDate);
    }

    /**
     * Get total salary payments for a user in a specific year
     *
     * @param userId User ID
     * @param year   Year
     * @return Total salary for the user in that year
     */
    public BigDecimal getTotalSalaryByUserAndYear(Long userId, int year) {
        return salaryRepository.getTotalSalaryByUserAndYear(userId, year);
    }




}

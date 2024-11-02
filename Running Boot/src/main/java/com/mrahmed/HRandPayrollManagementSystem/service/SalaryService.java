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


    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }


    public Salary updateSalary(Long salaryId, Salary salary) {
        if (!salaryRepository.existsById(salaryId)) {
            throw new RuntimeException("Salary record not found.");
        }
        salary.setId(salaryId);
        return salaryRepository.save(salary);
    }


    public void deleteSalary(Long salaryId) {
        if (!salaryRepository.existsById(salaryId)) {
            throw new RuntimeException("Salary record not found.");
        }
        salaryRepository.deleteById(salaryId);
    }


    public Salary getSalaryById(Long salaryId) {
        return salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary record not found."));
    }



    public BigDecimal calculateOvertimeSalary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Retrieve total overtime hours for the user in the given range
        BigDecimal totalOvertimeHours = salaryRepository.getTotalOvertimeHoursByUserAndDateRange(userId, startDate, endDate);
        if (totalOvertimeHours == null) {
            totalOvertimeHours = BigDecimal.ZERO;
        }

        // Get user and their basic salary
        User user = userService.findUserById(userId);
        BigDecimal basicSalary = user.getBasicSalary();

        // Ensure basic salary is not zero to avoid division by zero
        if (basicSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Basic salary must be greater than zero.");
        }

        // Calculate hourly rate: basic salary / (4 weeks * 5 days * 8 hours)
        BigDecimal hourlyRate = basicSalary.divide(BigDecimal.valueOf(160), BigDecimal.ROUND_HALF_UP);

        // Calculate overtime salary = overtime hours * hourly rate
        return totalOvertimeHours.multiply(hourlyRate);
    }



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

    public List<Salary> getSalariesByUserAndYear(Long userId, int year) {
        return salaryRepository.findSalariesByUserAndYear(userId, year);
    }


    public BigDecimal getTotalOvertimeHours(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return salaryRepository.getTotalOvertimeHoursByUserAndDateRange(userId, startDate, endDate);
    }


    public List<Salary> getSalariesByUserYearAndMonth(Long userId, int year, Month payrollMonth) {
        return salaryRepository.findSalariesByUserYearAndMonth(userId, year, payrollMonth);
    }


    public List<Salary> getSalariesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return salaryRepository.findSalariesByDateRange(startDate, endDate);
    }


    public BigDecimal getTotalSalaryByUserAndYear(Long userId, int year) {
        return salaryRepository.getTotalSalaryByUserAndYear(userId, year);
    }




}

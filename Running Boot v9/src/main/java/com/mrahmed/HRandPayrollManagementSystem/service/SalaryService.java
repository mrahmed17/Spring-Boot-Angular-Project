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
        BigDecimal totalOvertimeHours = salaryRepository.getTotalOvertimeHoursByUserAndDateRange(userId, startDate, endDate);
        if (totalOvertimeHours == null) {
            totalOvertimeHours = BigDecimal.ZERO;
        }
        User user = userService.findUserById(userId);
        BigDecimal basicSalary = user.getBasicSalary();
        if (basicSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Basic salary must be greater than zero.");
        }
        BigDecimal hourlyRate = basicSalary.divide(BigDecimal.valueOf(160), BigDecimal.ROUND_HALF_UP);
        return totalOvertimeHours.multiply(hourlyRate);
    }



    @Transactional
    public BigDecimal calculateTotalSalary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Salary> latestSalaries = salaryRepository.findLatestSalaryByUser(userId);
        if (latestSalaries.isEmpty()) {
            throw new RuntimeException("No salary record found for the user.");
        }
        Salary latestSalary = latestSalaries.get(0);
        BigDecimal overtimeSalary = calculateOvertimeSalary(userId, startDate, endDate);

        BigDecimal totalBonuses = latestSalary.getBonuses().stream()
                .map(Bonus::getBonusAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalAllowances = latestSalary.getTransportAllowance()
                .add(latestSalary.getTelephoneSubsidy())
                .add(latestSalary.getUtilityAllowance())
                .add(latestSalary.getDomesticAllowance())
                .add(latestSalary.getLunchAllowance());
        BigDecimal totalDeductions = latestSalary.getMedicare()
                .add(latestSalary.getProvidentFund())
                .add(latestSalary.getInsurance())
                .add(latestSalary.getTax());
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

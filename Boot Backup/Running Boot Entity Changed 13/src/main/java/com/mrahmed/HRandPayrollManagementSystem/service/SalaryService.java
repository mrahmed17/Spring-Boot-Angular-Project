package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.*;
import com.mrahmed.HRandPayrollManagementSystem.repository.AttendanceRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.SalaryRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    private static final double OVERTIME_RATE_MULTIPLIER = 1.5;
    private static final int STANDARD_HOURS = 8;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Create a new Salary
    public Salary createSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    public Salary updateSalary(Long salaryId, Salary updatedSalary) {
        Salary existingSalary = getSalaryById(salaryId).orElseThrow(() -> new RuntimeException("Salary record not found."));
        updateSalaryFields(existingSalary, updatedSalary);
        return salaryRepository.save(existingSalary);
    }


    // Retrieve Salary by ID
    public Optional<Salary> getSalaryById(Long salaryId) {
        return salaryRepository.findById(salaryId);
    }

    // Retrieve all Salaries
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    // Delete a Salary by ID
    @Transactional
    public void deleteSalary(Long salaryId) {
        if (!salaryRepository.existsById(salaryId)) {
            throw new RuntimeException("Salary record not found.");
        }
        salaryRepository.deleteById(salaryId);
    }

    // Total Salary Calculation
    @Transactional
    public double calculateTotalSalary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        Salary latestSalary = getLatestSalaryForUser(userId);
        double totalAllowances = calculateTotalAllowances(latestSalary);
        double totalDeductions = calculateTotalDeductions(latestSalary);
        double grossSalary = latestSalary.getNetSalary() - totalDeductions;
        double overtimeHours = calculateOvertimeHours(attendanceRepository.findAttendancesByUserIdAndDateRange(userId, startDate, endDate));
        double overtimeSalary = overtimeHours * getHourlyRate(userId) * OVERTIME_RATE_MULTIPLIER;
        return grossSalary + overtimeSalary + totalAllowances;
    }


    private void updateSalaryFields(Salary existingSalary, Salary updatedSalary) {
        existingSalary.setNetSalary(updatedSalary.getNetSalary());
        existingSalary.setPaymentDate(updatedSalary.getPaymentDate());
        existingSalary.setMedicare(updatedSalary.getMedicare());
        existingSalary.setProvidentFund(updatedSalary.getProvidentFund());
        existingSalary.setInsurance(updatedSalary.getInsurance());
        existingSalary.setTransportAllowance(updatedSalary.getTransportAllowance());
        existingSalary.setTelephoneSubsidy(updatedSalary.getTelephoneSubsidy());
        existingSalary.setUtilityAllowance(updatedSalary.getUtilityAllowance());
        existingSalary.setDomesticAllowance(updatedSalary.getDomesticAllowance());
        existingSalary.setLunchAllowance(updatedSalary.getLunchAllowance());
        existingSalary.setTax(updatedSalary.getTax());
    }


    private Salary getLatestSalaryForUser(Long userId) {
        return salaryRepository.findLatestSalaryByUser(userId).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No salary record found for the user."));
    }


    private double calculateTotalAllowances(Salary salary) {
        return salary.getTransportAllowance() +
                salary.getTelephoneSubsidy() +
                salary.getUtilityAllowance() +
                salary.getDomesticAllowance() +
                salary.getLunchAllowance();
    }

    private double calculateTotalDeductions(Salary salary) {
        return salary.getMedicare() +
                salary.getProvidentFund() +
                salary.getInsurance() +
                salary.getTax();
    }


    // Calculate Overtime Salary
    public double calculateOvertimeSalary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        double totalOvertimeHours = getTotalOvertimeHours(userId, startDate, endDate);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double hourlyRate = user.getBasicSalary() / (4 * 5 * STANDARD_HOURS); // 4 weeks, 5 days, 8 hours
        return totalOvertimeHours * hourlyRate * OVERTIME_RATE_MULTIPLIER;
    }


    public double getTotalOvertimeHours(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Attendance> attendances = attendanceRepository.findAttendancesByUserIdAndDateRange(userId, startDate, endDate);
        return attendances.stream()
                .filter(a -> a.getClockOutTime().minusHours(a.getClockInTime().getHour()).getHour() > STANDARD_HOURS)
                .mapToDouble(a -> a.getClockOutTime().minusHours(a.getClockInTime().getHour()).getHour() - STANDARD_HOURS)
                .sum();
    }


    public double getHourlyRate(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBasicSalary() / (4 * 5 * STANDARD_HOURS); // 4 weeks, 5 days, 8 hours
    }


    public List<Salary> getSalariesByUserId(Long userId) {
        return salaryRepository.findSalariesByUserId(userId);
    }

    public List<Salary> getSalariesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return salaryRepository.findSalariesByDateRange(startDate, endDate);
    }


    private double calculateTransportAllowance() {
        return 500.0;
    }

    private double calculateTelephoneSubsidy() {
        return 200.0;
    }

    private double calculateUtilityAllowance() {
        return 300.0;
    }

    private double calculateDomesticAllowance() {
        return 400.0;
    }

    private double calculateLunchAllowance() {
        return 150.0;
    }

    private double calculateTax(double baseSalary, double medicare) {
        return (baseSalary - medicare) * 0.1;
    }

    private double calculateNetSalary(double baseSalary, double totalOvertimeHours) {
        double overtimePay = totalOvertimeHours * (baseSalary / (4 * 5 * 8)); // 4 weeks, 5 days a week, 8 hours a day
        double totalAllowances = calculateTransportAllowance() + calculateTelephoneSubsidy() + calculateUtilityAllowance()
                + calculateDomesticAllowance() + calculateLunchAllowance();
        double deductions = calculateTax(baseSalary, calculateMedicare(baseSalary)) + calculateProvidentFund(baseSalary)
                + calculateInsurance(baseSalary);
        return baseSalary + overtimePay + totalAllowances - deductions;
    }


    private double calculateMedicare(double baseSalary) {
        return baseSalary * 0.015;
    }

    private double calculateInsurance(double baseSalary) {
        return baseSalary * 0.02;
    }

    private double calculateProvidentFund(double baseSalary) {
        return baseSalary * 0.02;
    }

    private void populateSalaryFields(Salary salary, Long userId, double baseSalary, List<Attendance> attendanceRecords, double totalOvertimeHours) {
        salary.setUser(userRepository.findById(userId).orElse(null));
        salary.setPaymentDate(LocalDateTime.now());
        salary.setNetSalary(calculateNetSalary(baseSalary, totalOvertimeHours));
        salary.setMedicare(calculateMedicare(baseSalary));
        salary.setProvidentFund(baseSalary * 0.02); // Assuming a fixed percentage
        salary.setInsurance(calculateInsurance(baseSalary));
        salary.setTransportAllowance(calculateTransportAllowance());
        salary.setTelephoneSubsidy(calculateTelephoneSubsidy());
        salary.setUtilityAllowance(calculateUtilityAllowance());
        salary.setDomesticAllowance(calculateDomesticAllowance());
        salary.setLunchAllowance(calculateLunchAllowance());
        salary.setTax(calculateTax(baseSalary, salary.getMedicare()));
    }


    private double calculateOvertimeHours(List<Attendance> attendanceRecords) {
        return attendanceRecords.stream()
                .filter(a -> a.getClockOutTime().minusHours(a.getClockInTime().getHour()).getHour() > STANDARD_HOURS)
                .mapToDouble(a -> a.getClockOutTime().minusHours(a.getClockInTime().getHour()).getHour() - STANDARD_HOURS)
                .sum();
    }


    public Optional<Salary> findById(Long id) {
        return salaryRepository.findById(id);
    }


    // Method to find salaries by full name
    public List<Salary> findByFullName(String name) {
        return salaryRepository.findByFullName(name);
    }

    // Method to find salary by email
    public Optional<Salary> findByEmail(String email) {
        return salaryRepository.findByEmail(email);
    }


}

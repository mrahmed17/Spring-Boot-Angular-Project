package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.*;
import com.mrahmed.HRandPayrollManagementSystem.repository.AttendanceRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.SalaryRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        double overtimeSalary = calculateOvertimeSalary(userId, startDate, endDate);
        double totalBonuses = calculateTotalBonuses(latestSalary);
        double totalAllowances = calculateTotalAllowances(latestSalary);
        double totalDeductions = calculateTotalDeductions(latestSalary);

        return latestSalary.getNetSalary() + overtimeSalary + totalBonuses + totalAllowances - totalDeductions;
    }

    private void updateSalaryFields(Salary existingSalary, Salary updatedSalary) {
        existingSalary.setNetSalary(updatedSalary.getNetSalary());
        existingSalary.setPayrollMonth(updatedSalary.getPayrollMonth());
        existingSalary.setYear(updatedSalary.getYear());
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



    private double calculateTotalBonuses(Salary salary) {
        return salary.getBonuses().stream()
                .mapToDouble(Bonus::getBonusAmount)
                .sum();
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

    public List<Salary> getSalariesByUserAndYear(Long userId, int year) {
        return salaryRepository.findSalariesByUserAndYear(userId, year);
    }

    // Change access modifier to public or protected
    public double getTotalOvertimeHours(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Attendance> attendances = attendanceRepository.findAttendancesByUserIdAndDateRange(userId, startDate, endDate);
        return attendances.stream()
                .filter(a -> a.getClockOutTime().minusHours(a.getClockInTime().getHour()).getHour() > STANDARD_HOURS)
                .mapToDouble(a -> a.getClockOutTime().minusHours(a.getClockInTime().getHour()).getHour() - STANDARD_HOURS)
                .sum();
    }


    // Additional methods for queries and reports
    public double getTotalSalaryByMonth(Month month) {
        return salaryRepository.getTotalSalaryByMonth(month);
    }

    public double getTotalSalaryByYear(int year) {
        return salaryRepository.getTotalSalaryByYear(year);
    }

    public double getTotalSalaryByUserAndYear(Long userId, int year) {
        return salaryRepository.getTotalSalaryByUserAndYear(userId, year);
    }

    public List<Salary> getSalariesByUserId(Long userId) {
        return salaryRepository.findSalariesByUserId(userId);
    }

    public List<Salary> getSalariesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return salaryRepository.findSalariesByDateRange(startDate, endDate);
    }

    public Optional<User> getUserWithHighestMonthlySalary(Month month) {
        return salaryRepository.findUserWithHighestMonthlySalary(month);
    }

    public Optional<User> getUserWithHighestYearlySalary(int year) {
        return salaryRepository.findUserWithHighestYearlySalary(year);
    }

    public Salary calculateSalary(Long userId, double baseSalary, int year, Month payrollMonth) {
        List<Attendance> attendanceRecords = attendanceRepository.findAllByUserId(userId);
        double totalOvertimeHours = calculateOvertimeHours(attendanceRecords);

        Salary salary = new Salary();
        populateSalaryFields(salary, userId, baseSalary, year, payrollMonth, attendanceRecords, totalOvertimeHours);

        return salaryRepository.save(salary);
    }

    private double calculateTransportAllowance() {
        // Implement your logic for calculating transport allowance
        return 500.0; // Example value
    }

    private double calculateTelephoneSubsidy() {
        // Implement your logic for calculating telephone subsidy
        return 200.0; // Example value
    }

    private double calculateUtilityAllowance() {
        // Implement your logic for calculating utility allowance
        return 300.0; // Example value
    }

    private double calculateDomesticAllowance() {
        // Implement your logic for calculating domestic allowance
        return 400.0; // Example value
    }

    private double calculateLunchAllowance() {
        // Implement your logic for calculating lunch allowance
        return 150.0; // Example value
    }

    private double calculateTax(double baseSalary, double medicare) {
        // Implement your tax calculation logic based on baseSalary and medicare
        return (baseSalary - medicare) * 0.1; // Example: 10% tax
    }

    // Method to calculate the net salary after all deductions and allowances
    private double calculateNetSalary(double baseSalary, double totalOvertimeHours) {
        double overtimePay = totalOvertimeHours * (baseSalary / (4 * 5 * 8)); // 4 weeks, 5 days a week, 8 hours a day
        double totalAllowances = calculateTransportAllowance() + calculateTelephoneSubsidy() + calculateUtilityAllowance()
                + calculateDomesticAllowance() + calculateLunchAllowance();
        double deductions = calculateTax(baseSalary, calculateMedicare(baseSalary)) + calculateProvidentFund(baseSalary)
                + calculateInsurance(baseSalary);
        return baseSalary + overtimePay + totalAllowances - deductions;
    }


    private double calculateMedicare(double baseSalary) {
        // Implement medicare calculation logic
        return baseSalary * 0.015; // Example: 1.5% of baseSalary
    }

    private double calculateInsurance(double baseSalary) {
        // Implement insurance calculation logic
        return baseSalary * 0.02; // Example: 2% of baseSalary
    }

    private double calculateProvidentFund(double baseSalary) {
        // Implement provident fund logic
        return baseSalary * 0.02; // Example: 2% of baseSalary
    }

    private void populateSalaryFields(Salary salary, Long userId, double baseSalary, int year, Month payrollMonth, List<Attendance> attendanceRecords, double totalOvertimeHours) {
        salary.setUser(userRepository.findById(userId).orElse(null));
        salary.setPaymentDate(LocalDateTime.now());
        salary.setYear(year);
        salary.setPayrollMonth(payrollMonth);
        salary.setOverTime(attendanceRecords);
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


    // Implement the remaining calculation methods for allowances and tax...

//    public Salary getSalaryId(Long userId) {
//        return salaryRepository.findBySalaryId(userId);
//    }

    public Optional<Salary> findById(Long id) {
        return salaryRepository.findById(id);
    }

    public List<Attendance> findAttendanceByDepartment() {
        return attendanceRepository.findAttendanceByDepartment();
    }

    LocalDate startDate = LocalDate.of(2023, 9, 1);
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    public List<Attendance> findAttendanceHistoryForUser(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> findAttendanceHistoryForUser = attendanceRepository.findAttendanceHistoryForUser(userId, startDate, endDate);
        return findAttendanceHistoryForUser;
    }


    // Method to find salaries by full name
    public List<Salary> findByFullName(String name) {
        return salaryRepository.findByFullName(name);
    }

    // Method to find salary by email
    public Optional<Salary> findByEmail(String email) {
        return salaryRepository.findByEmail(email);
    }

    // Method to find salaries by payroll month
    public List<Salary> findByPayrollMonth(Month month) {
        return salaryRepository.findByPayrollMonth(month);
    }



}

package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.AdvanceSalary;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.repository.AdvanceSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdvanceSalaryService {

    @Autowired
    private AdvanceSalaryRepository advanceSalaryRepository;

    // Create a new advance salary record
    public AdvanceSalary createAdvanceSalary(AdvanceSalary advanceSalary) {
        return advanceSalaryRepository.save(advanceSalary);
    }

    // Retrieve an advance salary record by ID
    public Optional<AdvanceSalary> getAdvanceSalaryById(Long id) {
        return advanceSalaryRepository.findById(id);
    }

    // Retrieve all advance salaries
    public List<AdvanceSalary> getAllAdvanceSalaries() {
        return advanceSalaryRepository.findAll();
    }

    // Update an existing advance salary record
    public AdvanceSalary updateAdvanceSalary(AdvanceSalary advanceSalary) {
        if (advanceSalaryRepository.existsById(advanceSalary.getId())) {
            return advanceSalaryRepository.save(advanceSalary);
        } else {
            throw new IllegalArgumentException("AdvanceSalary with ID " + advanceSalary.getId() + " does not exist.");
        }
    }

    // Delete an advance salary record by ID
    public void deleteAdvanceSalary(Long id) {
        if (advanceSalaryRepository.existsById(id)) {
            advanceSalaryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("AdvanceSalary with ID " + id + " does not exist.");
        }
    }

    // Retrieve advance salaries by user email
    public Optional<AdvanceSalary> getAdvanceSalariesByEmail(String userEmail) {
        return advanceSalaryRepository.findAdvanceSalariesByEmail(userEmail);
    }

    // Retrieve advance salaries by user full name or part of the name
    public List<AdvanceSalary> getAdvanceSalariesByName(String name) {
        return advanceSalaryRepository.findAdvanceSalariesByName(name);
    }

    // Calculate total advance salary by user ID and name (if needed)
    public double getTotalAdvanceSalaryByName(Long userId) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByName(userId);
    }

    // Retrieve advance salaries by user ID
    public List<AdvanceSalary> getAdvanceSalariesByUserId(Long userId) {
        return advanceSalaryRepository.findAdvanceSalariesByUserId(userId);
    }

    // Calculate total advance salary by user ID
    public double getTotalAdvanceSalaryByUserId(Long userId) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByUserId(userId);
    }

    // Retrieve advance salaries by month
    public List<AdvanceSalary> getAdvanceSalariesByMonth(Month month) {
        return advanceSalaryRepository.findAdvanceSalariesByMonth(month);
    }

    // Calculate total advance salary for a specific month
    public double getTotalAdvanceSalaryByMonth(Month month) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByMonth(month.name());  // Assuming month.name() is passed as string
    }

    // Retrieve advance salaries by year
    public List<AdvanceSalary> getAdvanceSalariesByYear(int year) {
        return advanceSalaryRepository.findAdvanceSalariesByYear(year);
    }

    // Calculate total advance salary for a specific year
    public double getTotalAdvanceSalaryByYear(int year) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByYear(year);
    }

    // Retrieve advance salaries within a specific date range
    public List<AdvanceSalary> getAdvanceSalariesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return advanceSalaryRepository.findAdvanceSalariesByDateRange(startDate, endDate);
    }

    // Retrieve the latest advance salary record for a specific user
    public Optional<AdvanceSalary> getLatestAdvanceSalaryByUserId(Long userId) {
        List<AdvanceSalary> salaries = advanceSalaryRepository.findLatestAdvanceSalaryByUserId(userId);
        return salaries.isEmpty() ? Optional.empty() : Optional.of(salaries.get(0));
    }

    // Get distinct users who received advance salary in a specific year
    public List<Long> getUsersWhoReceivedAdvanceSalaryInYear(int year) {
        return advanceSalaryRepository.getUsersWhoReceivedAdvanceSalaryInYear(year);
    }

    // Count the number of advance salaries for a specific user in a given year
    public int countAdvanceSalariesForUserInYear(Long userId, int year) {
        return advanceSalaryRepository.countAdvanceSalariesForUserInYear(userId, year);
    }


}
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

    public AdvanceSalary createAdvanceSalary(AdvanceSalary advanceSalary) {
        return advanceSalaryRepository.save(advanceSalary);
    }

    public Optional<AdvanceSalary> getAdvanceSalaryById(Long id) {
        return advanceSalaryRepository.findById(id);
    }

    public List<AdvanceSalary> getAllAdvanceSalaries() {
        return advanceSalaryRepository.findAll();
    }

    public AdvanceSalary updateAdvanceSalary(AdvanceSalary advanceSalary) {
        if (advanceSalaryRepository.existsById(advanceSalary.getId())) {
            return advanceSalaryRepository.save(advanceSalary);
        } else {
            throw new IllegalArgumentException("AdvanceSalary with ID " + advanceSalary.getId() + " does not exist.");
        }
    }

    public void deleteAdvanceSalary(Long id) {
        if (advanceSalaryRepository.existsById(id)) {
            advanceSalaryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("AdvanceSalary with ID " + id + " does not exist.");
        }
    }

    public Optional<AdvanceSalary> getAdvanceSalariesByEmail(String userEmail) {
        return advanceSalaryRepository.findAdvanceSalariesByEmail(userEmail);
    }

    public List<AdvanceSalary> getAdvanceSalariesByName(String name) {
        return advanceSalaryRepository.findAdvanceSalariesByName(name);
    }

    public double getTotalAdvanceSalaryByName(Long userId) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByName(userId);
    }


    public List<AdvanceSalary> getAdvanceSalariesByUserId(Long userId) {
        return advanceSalaryRepository.findAdvanceSalariesByUserId(userId);
    }

    public double getTotalAdvanceSalaryByUserId(Long userId) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByUserId(userId);
    }

    public List<AdvanceSalary> getAdvanceSalariesByMonth(Month month) {
        return advanceSalaryRepository.findAdvanceSalariesByMonth(month);
    }


    public double getTotalAdvanceSalaryByMonth(Month month) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByMonth(month.name());  // Assuming month.name() is passed as string
    }

    public List<AdvanceSalary> getAdvanceSalariesByYear(int year) {
        return advanceSalaryRepository.findAdvanceSalariesByYear(year);
    }


    public double getTotalAdvanceSalaryByYear(int year) {
        return advanceSalaryRepository.getTotalAdvanceSalaryByYear(year);
    }

    public List<AdvanceSalary> getAdvanceSalariesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return advanceSalaryRepository.findAdvanceSalariesByDateRange(startDate, endDate);
    }


    public Optional<AdvanceSalary> getLatestAdvanceSalaryByUserId(Long userId) {
        List<AdvanceSalary> salaries = advanceSalaryRepository.findLatestAdvanceSalaryByUserId(userId);
        return salaries.isEmpty() ? Optional.empty() : Optional.of(salaries.get(0));
    }


    public List<Long> getUsersWhoReceivedAdvanceSalaryInYear(int year) {
        return advanceSalaryRepository.getUsersWhoReceivedAdvanceSalaryInYear(year);
    }


    public int countAdvanceSalariesForUserInYear(Long userId, int year) {
        return advanceSalaryRepository.countAdvanceSalariesForUserInYear(userId, year);
    }


}
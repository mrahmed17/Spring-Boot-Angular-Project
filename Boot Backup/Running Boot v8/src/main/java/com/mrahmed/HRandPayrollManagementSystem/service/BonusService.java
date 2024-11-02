package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import com.mrahmed.HRandPayrollManagementSystem.entity.LeaveType;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.repository.BonusRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class BonusService {
    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    public Bonus saveBonus(Bonus bonus) {
        return bonusRepository.save(bonus);
    }


    public Bonus updateBonus(Long id, Bonus updatedBonus) {
        if (bonusRepository.existsById(id)) {
            updatedBonus.setId(id); // Ensure the ID is set for the update
            return bonusRepository.save(updatedBonus);
        } else {
            throw new RuntimeException("Bonus not found with id " + id);
        }
    }


    // Calculate bonus for a user in a specific year considering unpaid leaves
    public BigDecimal calculateBonus(Long userId, int year) {
        // Fetch total unpaid leave days for the user
        int totalUnpaidLeaveDays = leaveRepository.getTotalUnpaidLeaveDays(
                userId,
                Arrays.asList(LeaveType.SICK_UNPAID, LeaveType.RESERVE_UNPAID),
                year
        );

        // Fetch base bonus for the user and year
        BigDecimal baseBonus = bonusRepository.getBonusForUserAndYear(userId, year);

        // If no base bonus is found, return zero
        if (baseBonus == null) {
            baseBonus = BigDecimal.ZERO;
        }

        // Calculate deduction based on unpaid leave days
        BigDecimal deduction = calculateLeaveBonusDeduction(totalUnpaidLeaveDays);

        // Return the final bonus after deduction
        return baseBonus.subtract(deduction);
    }

    // Deduction logic for unpaid leave days
    private BigDecimal calculateLeaveBonusDeduction(int totalUnpaidLeaveDays) {
        BigDecimal deductionPerDay = new BigDecimal("50"); // Example: Deduct $50 per unpaid leave day
        return deductionPerDay.multiply(BigDecimal.valueOf(totalUnpaidLeaveDays));
    }

    // Get total bonus for a user for a specific year
    public BigDecimal getTotalBonusForUser(Long userId, int year) {
        BigDecimal totalBonus = bonusRepository.getTotalBonusForUserAndYear(userId, year);
        return totalBonus != null ? totalBonus : BigDecimal.ZERO;
    }

    // Get all bonuses for a specific month and year
    public List<Bonus> getBonusesByMonthAndYear(Month month, int year) {
        return bonusRepository.getBonusesByMonthAndYear(month, year);
    }

    // Get the total bonus paid in a specific year
    public BigDecimal getTotalBonusPaidInYear(int year) {
        return bonusRepository.getTotalBonusPaidInYear(year);
    }

    // Get bonuses for a user for a specific month and year
    public Bonus getBonusForUserByMonthAndYear(Long userId, Month month, int year) {
        return bonusRepository.getBonusForUserByMonthAndYear(userId, month, year);
    }

    // Get bonuses between a date range
    public List<Bonus> getBonusesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return bonusRepository.getBonusesBetweenDates(startDate, endDate);
    }

    // Get users who received a bonus in a specific year
    public List<Long> getUsersWhoReceivedBonusInYear(int year) {
        return bonusRepository.getUsersWhoReceivedBonusInYear(year);
    }

    // Get the latest bonus for a user
    public Bonus getLatestBonusForUser(Long userId) {
        return bonusRepository.getLatestBonusForUser(userId);
    }

    // Count the number of bonuses for a user in a specific year
    public int countBonusesForUserInYear(Long userId, int year) {
        return bonusRepository.countBonusesForUserInYear(userId, year);
    }

    // Get total bonus for a specific month and year
    public BigDecimal getTotalBonusForMonthAndYear(Month month, int year) {
        return bonusRepository.getTotalBonusForMonthAndYear(month, year);
    }


}

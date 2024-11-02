package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import com.mrahmed.HRandPayrollManagementSystem.entity.LeaveType;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.repository.BonusRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BonusService {
    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private LeaveRepository leaveRepository;


    // Create a new Bonus
    public Bonus createBonus(Bonus bonus) {
        return bonusRepository.save(bonus);
    }

    // Read/Get a Bonus by ID
    public Optional<Bonus> getBonusById(Long id) {
        return bonusRepository.findById(id);
    }

    // Update an existing Bonus
    public Bonus updateBonus(Long id, Bonus updatedBonus) {
        Optional<Bonus> bonusOptional = bonusRepository.findById(id);
        if (bonusOptional.isPresent()) {
            Bonus existingBonus = bonusOptional.get();
            existingBonus.setBonusAmount(updatedBonus.getBonusAmount());
            existingBonus.setBonusMonth(updatedBonus.getBonusMonth());
            existingBonus.setYear(updatedBonus.getYear());
            existingBonus.setBonusDate(updatedBonus.getBonusDate());
            existingBonus.setUser(updatedBonus.getUser());
            return bonusRepository.save(existingBonus);
        } else {
            return null;
        }
    }


    // Delete a Bonus by ID
    public void deleteBonus(Long id) {
        bonusRepository.deleteById(id);
    }

    // getAllBonuses
    public List<Bonus> getAllBonuses() {
        return bonusRepository.findAll();
    }

    // Calculate bonus for a user in a specific year considering unpaid leaves
    public double calculateBonus(Long userId) {
        // Define the list of unpaid leave types
        List<LeaveType> unpaidLeaveTypes = Arrays.asList(LeaveType.UNPAID, LeaveType.RESERVE);

        // Fetch total unpaid leave days for the user
        int totalUnpaidLeaveDays = leaveRepository.getTotalUnpaidLeaveDays(userId, unpaidLeaveTypes);

        // Fetch the user's base bonus for the year
        double baseBonus = bonusRepository.getTotalBonusByUserId(userId);

        // Calculate the deduction based on unpaid leave days
        double deduction = calculateLeaveBonusDeduction(totalUnpaidLeaveDays);

        // Return the final calculated bonus after applying the deduction
        return baseBonus - deduction;
    }

    // Deduction logic for unpaid leave days
    private double calculateLeaveBonusDeduction(int totalUnpaidLeaveDays) {
        double deductionPerDay = 1000.0; // Example: Deduct $1000 per unpaid leave day
        return deductionPerDay * totalUnpaidLeaveDays;
    }


    // Find bonuses by user's email
    public List<Bonus> findBonusesByEmail(String email) {
        return bonusRepository.findBonusesByEmail(email);
    }

    // Find bonuses by user's full name (partial match, case-insensitive)
    public List<Bonus> findBonusesByName(String name) {
        return bonusRepository.findBonusesByName(name);
    }

    // Calculate total bonuses for a user by name
    public double getTotalBonusByName(Long userId) {
        return bonusRepository.getTotalBonusByName(userId);
    }

    // Find bonuses by user ID
    public List<Bonus> findBonusesByUserId(Long userId) {
        return bonusRepository.findBonusesByUserId(userId);
    }

    // Calculate total bonuses for a specific user by user ID
    public double getTotalBonusByUserId(Long userId) {
        return bonusRepository.getTotalBonusByUserId(userId);
    }

    // Find bonuses by specific month
    public List<Bonus> findBonusesByMonth(Month month) {
        return bonusRepository.findBonusesByMonth(month);
    }

    // Calculate total bonuses for a specific month
    public double getTotalBonusByMonth(Month month) {
        return bonusRepository.getTotalBonusByMonth(month);
    }

    // Find bonuses by specific year
    public List<Bonus> findBonusesByYear(int year) {
        return bonusRepository.findBonusesByYear(year);
    }

    // Calculate total bonuses for a specific year
    public double getTotalBonusByYear(int year) {
        return bonusRepository.getTotalBonusByYear(year);
    }

    // Find bonuses within a specific date range
    public List<Bonus> findBonusesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bonusRepository.findBonusesByDateRange(startDate, endDate);
    }

    // Find the latest bonus for a user
    public List<Bonus> findLatestBonusByUserId(Long userId) {
        return bonusRepository.findLatestBonusByUserId(userId);
    }

    // Find distinct users who received bonuses in a specific year
    public List<Long> getUsersWhoReceivedBonusInYear(int year) {
        return bonusRepository.getUsersWhoReceivedBonusInYear(year);
    }

    // Count the number of bonuses for a specific user in a given year
    public int countBonusesForUserInYear(Long userId, int year) {
        return bonusRepository.countBonusesForUserInYear(userId, year);
    }

}

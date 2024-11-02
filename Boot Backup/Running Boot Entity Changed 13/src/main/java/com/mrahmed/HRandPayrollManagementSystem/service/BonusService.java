package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import com.mrahmed.HRandPayrollManagementSystem.entity.LeaveType;
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


    public Bonus createBonus(Bonus bonus) {
        return bonusRepository.save(bonus);
    }

    public Optional<Bonus> getBonusById(Long id) {
        return bonusRepository.findById(id);
    }

    public Bonus updateBonus(Long id, Bonus updatedBonus) {
        Optional<Bonus> bonusOptional = bonusRepository.findById(id);
        if (bonusOptional.isPresent()) {
            Bonus existingBonus = bonusOptional.get();
            existingBonus.setBonusAmount(updatedBonus.getBonusAmount());
            existingBonus.setBonusDate(updatedBonus.getBonusDate());
            existingBonus.setUser(updatedBonus.getUser());
            return bonusRepository.save(existingBonus);
        } else {
            return null;
        }
    }

    public void deleteBonus(Long id) {
        bonusRepository.deleteById(id);
    }

    public List<Bonus> getAllBonuses() {
        return bonusRepository.findAll();
    }

    public double calculateBonus(Long userId) {
        List<LeaveType> unpaidLeaveTypes = Arrays.asList(LeaveType.UNPAID, LeaveType.RESERVE);
        int totalUnpaidLeaveDays = leaveRepository.getTotalUnpaidLeaveDays(userId, unpaidLeaveTypes);
        double baseBonus = bonusRepository.getTotalBonusByUserId(userId);
        double deduction = calculateLeaveBonusDeduction(totalUnpaidLeaveDays);
        return baseBonus - deduction;
    }

    // Deduction logic for unpaid leave days
    private double calculateLeaveBonusDeduction(int totalUnpaidLeaveDays) {
        double deductionPerDay = 1000.0;
        return deductionPerDay * totalUnpaidLeaveDays;
    }
    public List<Bonus> findBonusesByEmail(String email) {
        return bonusRepository.findBonusesByEmail(email);
    }

    public List<Bonus> findBonusesByName(String name) {
        return bonusRepository.findBonusesByName(name);
    }

    public double getTotalBonusByName(Long userId) {
        return bonusRepository.getTotalBonusByName(userId);
    }

    public List<Bonus> findBonusesByUserId(Long userId) {
        return bonusRepository.findBonusesByUserId(userId);
    }

    public double getTotalBonusByUserId(Long userId) {
        return bonusRepository.getTotalBonusByUserId(userId);
    }

    public List<Bonus> findBonusesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bonusRepository.findBonusesByDateRange(startDate, endDate);
    }

    public List<Bonus> findLatestBonusByUserId(Long userId) {
        return bonusRepository.findLatestBonusByUserId(userId);
    }


}

package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import com.mrahmed.HRandPayrollManagementSystem.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bonuses")
@CrossOrigin("*")
public class BonusRestController {

    @Autowired
    private BonusService bonusService;

    // Calculate the bonus for a user in a specific year
    @GetMapping("/calculate/{userId}/{year}")
    public ResponseEntity<BigDecimal> calculateBonus(
            @PathVariable Long userId,
            @PathVariable int year
    ) {
        BigDecimal bonus = bonusService.calculateBonus(userId, year);
        return ResponseEntity.ok(bonus);
    }

    // Get total bonus for a user in a specific year
    @GetMapping("/total/{userId}/{year}")
    public ResponseEntity<BigDecimal> getTotalBonusForUser(
            @PathVariable Long userId,
            @PathVariable int year
    ) {
        BigDecimal totalBonus = bonusService.getTotalBonusForUser(userId, year);
        return ResponseEntity.ok(totalBonus);
    }

    // Get all bonuses for a specific month and year
    @GetMapping("/month/{month}/{year}")
    public ResponseEntity<List<Bonus>> getBonusesByMonthAndYear(
            @PathVariable Month month,
            @PathVariable int year
    ) {
        List<Bonus> bonuses = bonusService.getBonusesByMonthAndYear(month, year);
        return ResponseEntity.ok(bonuses);
    }

    // Get the total bonus paid in a specific year
    @GetMapping("/total/year/{year}")
    public ResponseEntity<BigDecimal> getTotalBonusPaidInYear(@PathVariable int year) {
        BigDecimal totalBonus = bonusService.getTotalBonusPaidInYear(year);
        return ResponseEntity.ok(totalBonus);
    }

    // Get bonus for a user for a specific month and year
    @GetMapping("/user/{userId}/month/{month}/{year}")
    public ResponseEntity<Bonus> getBonusForUserByMonthAndYear(
            @PathVariable Long userId,
            @PathVariable Month month,
            @PathVariable int year
    ) {
        Bonus bonus = bonusService.getBonusForUserByMonthAndYear(userId, month, year);
        return ResponseEntity.ok(bonus);
    }

    // Get all bonuses between two dates
    @GetMapping("/between")
    public ResponseEntity<List<Bonus>> getBonusesBetweenDates(
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate
    ) {
        List<Bonus> bonuses = bonusService.getBonusesBetweenDates(startDate, endDate);
        return ResponseEntity.ok(bonuses);
    }

    // Get users who received a bonus in a specific year
    @GetMapping("/users/year/{year}")
    public ResponseEntity<List<Long>> getUsersWhoReceivedBonusInYear(@PathVariable int year) {
        List<Long> userIds = bonusService.getUsersWhoReceivedBonusInYear(year);
        return ResponseEntity.ok(userIds);
    }

    // Get the latest bonus for a specific user
    @GetMapping("/latest/{userId}")
    public ResponseEntity<Bonus> getLatestBonusForUser(@PathVariable Long userId) {
        Bonus bonus = bonusService.getLatestBonusForUser(userId);
        return ResponseEntity.ok(bonus);
    }

    // Count the number of bonuses for a user in a specific year
    @GetMapping("/count/{userId}/{year}")
    public ResponseEntity<Integer> countBonusesForUserInYear(
            @PathVariable Long userId,
            @PathVariable int year
    ) {
        int count = bonusService.countBonusesForUserInYear(userId, year);
        return ResponseEntity.ok(count);
    }

    // Get total bonus for a specific month and year
    @GetMapping("/total/month/{month}/{year}")
    public ResponseEntity<BigDecimal> getTotalBonusForMonthAndYear(
            @PathVariable Month month,
            @PathVariable int year
    ) {
        BigDecimal totalBonus = bonusService.getTotalBonusForMonthAndYear(month, year);
        return ResponseEntity.ok(totalBonus);
    }


}

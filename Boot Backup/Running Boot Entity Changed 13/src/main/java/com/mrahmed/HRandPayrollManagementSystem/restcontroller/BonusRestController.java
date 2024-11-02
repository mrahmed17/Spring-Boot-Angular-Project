package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import com.mrahmed.HRandPayrollManagementSystem.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bonuses")
@CrossOrigin("*")
public class BonusRestController {

    @Autowired
    private BonusService bonusService;

    @PostMapping("/create")
    public ResponseEntity<Bonus> createBonus(@RequestBody Bonus bonus) {
        Bonus createdBonus = bonusService.createBonus(bonus);
        return new ResponseEntity<>(createdBonus, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Bonus> getBonusById(@PathVariable Long id) {
        return bonusService.getBonusById(id)
                .map(bonus -> new ResponseEntity<>(bonus, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Bonus> updateBonus(@PathVariable Long id, @RequestBody Bonus updatedBonus) {
        Bonus bonus = bonusService.updateBonus(id, updatedBonus);
        return bonus != null ? new ResponseEntity<>(bonus, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBonus(@PathVariable Long id) {
        bonusService.deleteBonus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bonus>> getAllBonuses() {
        List<Bonus> bonuses = bonusService.getAllBonuses();
        return new ResponseEntity<>(bonuses, HttpStatus.OK);
    }

    @GetMapping("/calculate/{userId}")
    public ResponseEntity<Double> calculateBonus(@PathVariable Long userId) {
        double bonusAmount = bonusService.calculateBonus(userId);
        return new ResponseEntity<>(bonusAmount, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Bonus>> findBonusesByEmail(@PathVariable String email) {
        List<Bonus> bonuses = bonusService.findBonusesByEmail(email);
        return new ResponseEntity<>(bonuses, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Bonus>> findBonusesByName(@PathVariable String name) {
        List<Bonus> bonuses = bonusService.findBonusesByName(name);
        return new ResponseEntity<>(bonuses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bonus>> findBonusesByUserId(@PathVariable Long userId) {
        List<Bonus> bonuses = bonusService.findBonusesByUserId(userId);
        return new ResponseEntity<>(bonuses, HttpStatus.OK);
    }

    @GetMapping("/dateRange")
    public ResponseEntity<List<Bonus>> findBonusesByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<Bonus> bonuses = bonusService.findBonusesByDateRange(startDate, endDate);
        return new ResponseEntity<>(bonuses, HttpStatus.OK);
    }

    @GetMapping("/latest/{userId}")
    public ResponseEntity<List<Bonus>> findLatestBonusByUserId(@PathVariable Long userId) {
        List<Bonus> bonuses = bonusService.findLatestBonusByUserId(userId);
        return new ResponseEntity<>(bonuses, HttpStatus.OK);
    }


    @GetMapping("/byName/{userId}")
    public ResponseEntity<Double> getTotalBonusByName(@PathVariable Long userId) {
        double totalBonus = bonusService.getTotalBonusByName(userId);
        return new ResponseEntity<>(totalBonus, HttpStatus.OK);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<Double> getTotalBonusByUserId(@PathVariable Long userId) {
        double totalBonus = bonusService.getTotalBonusByUserId(userId);
        return new ResponseEntity<>(totalBonus, HttpStatus.OK);
    }


}


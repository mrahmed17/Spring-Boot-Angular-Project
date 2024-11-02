package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.AdvanceSalary;
import com.mrahmed.HRandPayrollManagementSystem.service.AdvanceSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/advance-salary")
@CrossOrigin("*")
public class AdvanceSalaryRestController {

    @Autowired
    private AdvanceSalaryService advanceSalaryService;


    @PostMapping("/create")
    public ResponseEntity<AdvanceSalary> createAdvanceSalary(@RequestBody AdvanceSalary advanceSalary) {
        try {
            AdvanceSalary createdAdvanceSalary = advanceSalaryService.createAdvanceSalary(advanceSalary);
            return new ResponseEntity<>(createdAdvanceSalary, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AdvanceSalary> getAdvanceSalaryById(@PathVariable Long id) {
        Optional<AdvanceSalary> advanceSalary = advanceSalaryService.getAdvanceSalaryById(id);
        return advanceSalary
                .map(salary -> new ResponseEntity<>(salary, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdvanceSalary>> getAllAdvanceSalaries() {
        List<AdvanceSalary> salaries = advanceSalaryService.getAllAdvanceSalaries();
        return new ResponseEntity<>(salaries, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<AdvanceSalary> updateAdvanceSalary(@PathVariable Long id, @RequestBody AdvanceSalary advanceSalary) {
        try {
            advanceSalary.setId(id);
            AdvanceSalary updatedSalary = advanceSalaryService.updateAdvanceSalary(advanceSalary);
            return new ResponseEntity<>(updatedSalary, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdvanceSalary(@PathVariable Long id) {
        try {
            advanceSalaryService.deleteAdvanceSalary(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user-email/{email}")
    public ResponseEntity<AdvanceSalary> getAdvanceSalaryByEmail(@PathVariable String email) {
        Optional<AdvanceSalary> advanceSalary = advanceSalaryService.getAdvanceSalariesByEmail(email);
        return advanceSalary
                .map(salary -> new ResponseEntity<>(salary, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user-name/{name}")
    public ResponseEntity<List<AdvanceSalary>> getAdvanceSalariesByName(@PathVariable String name) {
        List<AdvanceSalary> salaries = advanceSalaryService.getAdvanceSalariesByName(name);
        return new ResponseEntity<>(salaries, HttpStatus.OK);
    }

       @GetMapping("/user-total-by-name/{userId}")
    public ResponseEntity<Double> getTotalAdvanceSalaryByName(@PathVariable Long userId) {
        try {
            double totalSalary = advanceSalaryService.getTotalAdvanceSalaryByName(userId);
            return new ResponseEntity<>(totalSalary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


     @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdvanceSalary>> getAdvanceSalariesByUserId(@PathVariable Long userId) {
        List<AdvanceSalary> salaries = advanceSalaryService.getAdvanceSalariesByUserId(userId);
        return new ResponseEntity<>(salaries, HttpStatus.OK);
    }


     @GetMapping("/user-total/{userId}")
    public ResponseEntity<Double> getTotalAdvanceSalaryByUserId(@PathVariable Long userId) {
        double totalSalary = advanceSalaryService.getTotalAdvanceSalaryByUserId(userId);
        return new ResponseEntity<>(totalSalary, HttpStatus.OK);
    }


     @GetMapping("/date-range")
    public ResponseEntity<List<AdvanceSalary>> getAdvanceSalariesByDateRange(@RequestParam LocalDateTime startDate,
                                                                             @RequestParam LocalDateTime endDate) {
        List<AdvanceSalary> salaries = advanceSalaryService.getAdvanceSalariesByDateRange(startDate, endDate);
        return new ResponseEntity<>(salaries, HttpStatus.OK);
    }

      @GetMapping("/latest/{userId}")
    public ResponseEntity<AdvanceSalary> getLatestAdvanceSalaryByUserId(@PathVariable Long userId) {
        Optional<AdvanceSalary> advanceSalary = advanceSalaryService.getLatestAdvanceSalaryByUserId(userId);
        return advanceSalary
                .map(salary -> new ResponseEntity<>(salary, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/branches")
@CrossOrigin("*")
public class BranchRestController {


    @Autowired
    private BranchService branchService;

    // Create a new branch
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createBranch(@RequestBody Branch branch) {
        branchService.createBranch(branch);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Branch created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all branches
    @GetMapping("/all")
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    // Get a branch by ID
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.getBranchById(id);
        return ResponseEntity.ok(branch);
    }

    // Update a branch
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBranch(@PathVariable Long id, @RequestBody Branch updatedBranch) {
        branchService.updateBranch(id, updatedBranch);
        return ResponseEntity.ok("Branch updated successfully.");
    }

    // Delete a branch
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Branch deleted successfully");
        return ResponseEntity.ok(response);
    }

    // Find branches by company ID
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Branch>> getBranchesByCompanyId(@PathVariable Long companyId) {
        List<Branch> branches = branchService.getBranchesByCompanyId(companyId);
        return ResponseEntity.ok(branches);
    }

    // Find branches by city
    @GetMapping("/search")
    public ResponseEntity<List<Branch>> getBranchesByCity(@RequestParam String city) {
        List<Branch> branches = branchService.getBranchesByCity(city);
        return ResponseEntity.ok(branches);
    }

    // Count departments in a branch
    @GetMapping("/{branchId}/departments/count")
    public ResponseEntity<Long> countDepartmentsInBranch(@PathVariable Long branchId) {
        long count = branchService.countDepartmentsInBranch(branchId);
        return ResponseEntity.ok(count);
    }

    // Paginate branches by company ID
    @GetMapping("/company/{companyId}/page")
    public ResponseEntity<Page<Branch>> getBranchesByCompanyIdPaginated(@PathVariable Long companyId, Pageable pageable) {
        Page<Branch> branches = branchService.getBranchesByCompanyId(companyId, pageable);
        return ResponseEntity.ok(branches);
    }


}

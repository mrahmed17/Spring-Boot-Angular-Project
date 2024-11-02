package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Company;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin("*")
public class CompanyRestController {

    @Autowired
    private CompanyService companyService;

    // Create Company
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createCompany(
            @RequestPart("company") Company company,
            @RequestPart(value = "companyPhoto", required = false) MultipartFile companyPhoto) {
        try {
            companyService.createCompany(company, companyPhoto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Company created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("message", "Failed to create company: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Company
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCompany(
            @PathVariable Long id,
            @RequestPart("company") Company companyDetails,
            @RequestPart(value = "companyPhoto", required = false) MultipartFile companyPhoto) {
        try {
            companyService.updateCompany(id, companyDetails, companyPhoto);
            return new ResponseEntity<>("Company updated successfully.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Company by ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        try {
            Company company = companyService.getCompanyById(id);
            return ResponseEntity.ok(company);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete Company
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Company deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("message", "Failed to delete company: " + e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // Retrieve All Companies
    @GetMapping("/all")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    // Find Company by Name
    @GetMapping("/search")
    public ResponseEntity<Company> findByCompanyName(@RequestParam String companyName) {
        Company company = companyService.findByCompanyName(companyName);
        return company != null ? ResponseEntity.ok(company) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Retrieve Branches by Company ID
    @GetMapping("/{companyId}/branches")
    public ResponseEntity<List<Branch>> getBranchesByCompanyId(@PathVariable Long companyId) {
        List<Branch> branches = companyService.getBranchesByCompanyId(companyId);
        return ResponseEntity.ok(branches);
    }

    // Retrieve Departments by Branch ID
    @GetMapping("/branches/{branchId}/departments")
    public ResponseEntity<List<Department>> getDepartmentsByBranchId(@PathVariable Long branchId) {
        List<Department> departments = companyService.getDepartmentsByBranchId(branchId);
        return ResponseEntity.ok(departments);
    }

    // Retrieve Departments by Company ID
    @GetMapping("/{companyId}/departments")
    public ResponseEntity<List<Department>> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        List<Department> departments = companyService.getDepartmentsByCompanyId(companyId);
        return ResponseEntity.ok(departments);
    }

    // Retrieve Employees by Department ID
    @GetMapping("/departments/{departmentId}/employees")
    public ResponseEntity<List<User>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<User> employees = companyService.getEmployeesByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }

    // Count Employees in a Company
    @GetMapping("/{companyId}/employee-count")
    public ResponseEntity<Long> countEmployeesByCompanyId(@PathVariable Long companyId) {
        long employeeCount = companyService.countEmployeesByCompanyId(companyId);
        return ResponseEntity.ok(employeeCount);
    }

    // Count Employees in a Branch
    @GetMapping("/branches/{branchId}/employee-count")
    public ResponseEntity<Long> countEmployeesByBranchId(@PathVariable Long branchId) {
        long employeeCount = companyService.countEmployeesByBranchId(branchId);
        return ResponseEntity.ok(employeeCount);
    }


}

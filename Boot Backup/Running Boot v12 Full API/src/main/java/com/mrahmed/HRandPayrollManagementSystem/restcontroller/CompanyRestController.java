package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Company;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin("*")
public class CompanyRestController {

    @Autowired
    private CompanyService companyService;

    // Create company with company photo upload
    @PostMapping("/create")
    public Company saveCompany(
            @RequestPart("company") Company company,
            @RequestPart(value = "companyPhoto", required = false) MultipartFile companyPhoto) throws IOException {
        return companyService.createCompany(company, companyPhoto);
    }

    @GetMapping("/find/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }


    @PutMapping("/update/{id}")
    public Company updateCompany(
            @PathVariable Long id,
            @RequestPart("company") Company companyDetails,
            @RequestPart(value = "companyPhoto", required = false) MultipartFile companyPhoto) throws IOException {
        return companyService.updateCompany(id, companyDetails, companyPhoto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

    @GetMapping("/all")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }


    // Find a company by name
    @GetMapping("/search")
    public Company findByCompanyName(@RequestParam String companyName) {
        return companyService.findByCompanyName(companyName);
    }

    // Get branches by company ID
    @GetMapping("/{companyId}/branches")
    public List<Branch> getBranchesByCompanyId(@PathVariable Long companyId) {
        return companyService.getBranchesByCompanyId(companyId);
    }

    // Get departments by branch ID
    @GetMapping("/branches/{branchId}/departments")
    public List<Department> getDepartmentsByBranchId(@PathVariable Long branchId) {
        return companyService.getDepartmentsByBranchId(branchId);
    }

    // Get departments by company ID
    @GetMapping("/{companyId}/departments")
    public List<Department> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        return companyService.getDepartmentsByCompanyId(companyId);
    }

    // Get employees by department ID
    @GetMapping("/departments/{departmentId}/employees")
    public List<User> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        return companyService.getEmployeesByDepartmentId(departmentId);
    }

    // Count employees in a company
    @GetMapping("/{companyId}/employee-count")
    public long countEmployeesByCompanyId(@PathVariable Long companyId) {
        return companyService.countEmployeesByCompanyId(companyId);
    }

    // Count employees in a branch
    @GetMapping("/branches/{branchId}/employee-count")
    public long countEmployeesByBranchId(@PathVariable Long branchId) {
        return companyService.countEmployeesByBranchId(branchId);
    }



}

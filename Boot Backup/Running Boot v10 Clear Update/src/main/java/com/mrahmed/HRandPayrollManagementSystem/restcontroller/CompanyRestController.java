package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Company;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin("*")
public class CompanyRestController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public Company saveCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @GetMapping("/find/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PutMapping("/update/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        return companyService.updateCompany(id, companyDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

    @GetMapping("/all")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }


    @GetMapping("/search")
    public Company findByCompanyName(@RequestParam String companyName) {
        return companyService.findByCompanyName(companyName);
    }


    @GetMapping("/{companyId}/branches")
    public List<Branch> getBranchesByCompanyId(@PathVariable Long companyId) {
        return companyService.getBranchesByCompanyId(companyId);
    }

    // Get departments by branch ID
    @GetMapping("/branches/{branchId}/departments")
    public List<Department> getDepartmentsByBranchId(@PathVariable Long branchId) {
        return companyService.getDepartmentsByBranchId(branchId);
    }

    @GetMapping("/{companyId}/departments")
    public List<Department> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        return companyService.getDepartmentsByCompanyId(companyId);
    }


    @GetMapping("/departments/{departmentId}/employees")
    public List<User> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        return companyService.getEmployeesByDepartmentId(departmentId);
    }


    @GetMapping("/{companyId}/employee-count")
    public long countEmployeesByCompanyId(@PathVariable Long companyId) {
        return companyService.countEmployeesByCompanyId(companyId);
    }

    @GetMapping("/branches/{branchId}/employee-count")
    public long countEmployeesByBranchId(@PathVariable Long branchId) {
        return companyService.countEmployeesByBranchId(branchId);
    }




}

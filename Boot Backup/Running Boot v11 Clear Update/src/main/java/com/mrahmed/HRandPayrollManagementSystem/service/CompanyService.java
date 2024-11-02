package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Company;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // Create a new company
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    // Update an existing company
    public Company updateCompany(Long id, Company updatedCompany) {
        Optional<Company> existingCompanyOpt = companyRepository.findById(id);
        if (existingCompanyOpt.isPresent()) {
            Company existingCompany = existingCompanyOpt.get();
            existingCompany.setCompanyName(updatedCompany.getCompanyName());
            existingCompany.setPhoto(updatedCompany.getPhoto());
            existingCompany.setBranches(updatedCompany.getBranches());
            return companyRepository.save(existingCompany);
        }
        return null;  // Handle the case where the company is not found
    }

    public Company getCompanyById(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            return optionalCompany.get();
        } else {
            throw new RuntimeException("Company not found with ID: " + id);
        }
    }

    @Transactional
    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;  // Company not found
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    // Get branches by company ID
    public List<Branch> getBranchesByCompanyId(Long companyId) {
        return companyRepository.findAllByCompanyId(companyId);
    }

    // Get departments by branch ID
    public List<Department> getDepartmentsByBranchId(Long branchId) {
        return companyRepository.findAllDepartmentsByBranchId(branchId);
    }

    // Get departments by company ID
    public List<Department> getDepartmentsByCompanyId(Long companyId) {
        return companyRepository.findAllDepartmentsByCompanyId(companyId);
    }

    // Get employees by department ID
    public List<User> getEmployeesByDepartmentId(Long departmentId) {
        return companyRepository.findAllEmployeesByDepartmentId(departmentId);
    }

    // Count total employees in a company
    public long countEmployeesByCompanyId(Long companyId) {
        return companyRepository.countTotalEmployeesByCompanyId(companyId);
    }

    // Count total employees in a branch
    public long countEmployeesByBranchId(Long branchId) {
        return companyRepository.countTotalEmployeesByBranchId(branchId);
    }





}

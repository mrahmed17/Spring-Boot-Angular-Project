package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    // Create a new Branch
    @Transactional
    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    // Get all branches
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    // Get Branch by ID
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + id));
    }

    // Update a Branch
    @Transactional
    public Branch updateBranch(Long id, Branch updatedBranch) {
        Branch existingBranch = getBranchById(id);
        existingBranch.setBranchName(updatedBranch.getBranchName());
        existingBranch.setCity(updatedBranch.getCity());
        existingBranch.setCountry(updatedBranch.getCountry());
        existingBranch.setCompany(updatedBranch.getCompany()); // Ensure you update the company if necessary
        return branchRepository.save(existingBranch);
    }

    // Delete a Branch
    @Transactional
    public void deleteBranch(Long id) {
        if (branchRepository.existsById(id)) {
            branchRepository.deleteById(id);
        } else {
            throw new RuntimeException("Branch not found with ID: " + id);
        }
    }

    // Find all branches by company ID
    public List<Branch> getBranchesByCompanyId(Long companyId) {
        return branchRepository.findAllByCompany_Id(companyId);
    }

    // Find branches by city (case-insensitive)
    public List<Branch> getBranchesByCity(String city) {
        return branchRepository.findByCityIgnoreCase(city);
    }

    // Count total number of departments in a specific branch
    public long countDepartmentsInBranch(Long branchId) {
        return branchRepository.countDepartmentsByBranchId(branchId);
    }

    // Paginate branches by company ID
    public Page<Branch> getBranchesByCompanyId(Long companyId, Pageable pageable) {
        return branchRepository.findAllByCompany_Id(companyId, pageable);
    }

}
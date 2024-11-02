package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public Branch createBranch(Branch branch) {
        branch.setCreatedAt(java.time.LocalDateTime.now());
        branch.setUpdateAt(java.time.LocalDateTime.now());
        return branchRepository.save(branch);
    }


    public Branch updateBranch(Long id, Branch updatedBranch) {
        Optional<Branch> existingBranchOpt = branchRepository.findById(id);
        if (existingBranchOpt.isPresent()) {
            Branch existingBranch = existingBranchOpt.get();
            existingBranch.setBranchName(updatedBranch.getBranchName());
            existingBranch.setAddress(updatedBranch.getAddress());
            existingBranch.setCity(updatedBranch.getCity());
            existingBranch.setZipCode(updatedBranch.getZipCode());
            existingBranch.setCountry(updatedBranch.getCountry());
            existingBranch.setPhoto(updatedBranch.getPhoto());
            existingBranch.setUpdateAt(java.time.LocalDateTime.now());
            return branchRepository.save(existingBranch);
        }
        return null;  // Handle the case where the branch is not found
    }

    public boolean deleteBranch(Long id) {
        if (branchRepository.existsById(id)) {
            branchRepository.deleteById(id);
            return true;
        }
        return false;  // Branch not found
    }

    public Branch getBranchById(Long id) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if (optionalBranch.isPresent()) {
            return optionalBranch.get();
        } else {
            throw new RuntimeException("Branch not found with ID: " + id);
        }
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch findByBranchName(String branchName) {
        return branchRepository.findByBranchName(branchName);
    }

    public List<Department> getDepartmentsByBranchId(Long branchId) {
        return branchRepository.findAllDepartmentsByBranchId(branchId);
    }

    public List<User> getEmployeesByBranchId(Long branchId) {
        return branchRepository.findAllEmployeesByBranchId(branchId);
    }

    public long countDepartmentsByBranchId(Long branchId) {
        return branchRepository.countTotalDepartmentsByBranchId(branchId);
    }

    public long countEmployeesByBranchId(Long branchId) {
        return branchRepository.countTotalEmployeesByBranchId(branchId);
    }

    public List<Branch> getBranchesByCompanyId(Long companyId) {
        return branchRepository.findAllByCompanyId(companyId);
    }

}

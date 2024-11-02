package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Value("${upload.directory}")
    private String uploadDir;

    public Branch createBranch(Branch branch, MultipartFile branchPhoto) throws IOException {
        branch.setCreatedAt(LocalDateTime.now());
        branch.setUpdateAt(LocalDateTime.now());

        if (branchPhoto != null && !branchPhoto.isEmpty()) {
            String filename = saveImage(branchPhoto, branch.getBranchName());
            branch.setPhoto(filename);
        }

        return branchRepository.save(branch);
    }

    // Method to save the image file and return the filename
    private String saveImage(MultipartFile file, String branchName) throws IOException {
        Path uploadPath = Paths.get(uploadDir, "branchPhoto");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String filename = branchName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + UUID.randomUUID() + fileExtension;
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        return filename;
    }


    public Branch updateBranch(Long id, Branch updatedBranch, MultipartFile branchPhoto) throws IOException {
        Optional<Branch> existingBranchOpt = branchRepository.findById(id);
        if (existingBranchOpt.isPresent()) {
            Branch existingBranch = existingBranchOpt.get();
            existingBranch.setBranchName(updatedBranch.getBranchName());
            existingBranch.setAddress(updatedBranch.getAddress());
            existingBranch.setCity(updatedBranch.getCity());
            existingBranch.setZipCode(updatedBranch.getZipCode());
            existingBranch.setCountry(updatedBranch.getCountry());
            existingBranch.setUpdateAt(LocalDateTime.now());

            if (branchPhoto != null && !branchPhoto.isEmpty()) {
                String filename = saveImage(branchPhoto, updatedBranch.getBranchName());
                existingBranch.setPhoto(filename);
            }

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

package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Company;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Value("${upload.directory}")
    private String uploadDir;

    // Create Company
    @Transactional
    public Company createCompany(Company company, MultipartFile companyPhoto) throws IOException {
        if (companyPhoto != null && !companyPhoto.isEmpty()) {
            String companyPhotoFilename = saveImage(companyPhoto, company.getCompanyName());
            company.setPhoto(companyPhotoFilename);
        }
        return companyRepository.save(company);
    }

    // Read Company by ID
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + id));
    }

    // Update Company
    @Transactional
    public Company updateCompany(Long id, Company updatedCompany, MultipartFile companyPhoto) throws IOException {
        Company existingCompany = getCompanyById(id);
        existingCompany.setCompanyName(updatedCompany.getCompanyName());

        if (companyPhoto != null && !companyPhoto.isEmpty()) {
            String companyPhotoFilename = saveImage(companyPhoto, updatedCompany.getCompanyName());
            existingCompany.setPhoto(companyPhotoFilename);
        }

        return companyRepository.save(existingCompany);
    }

    // Delete Company
    @Transactional
    public void deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Company not found with ID: " + id);
        }
    }

    // Get All Companies
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Find Company by Name
    public Company findByCompanyName(String companyName) {
        return companyRepository.findByCompanyNameIgnoreCase(companyName);
    }

    // Get Branches by Company ID
    public List<Branch> getBranchesByCompanyId(Long companyId) {
        return companyRepository.findAllByCompany_Id(companyId);
    }

    // Get Departments by Branch ID
    public List<Department> getDepartmentsByBranchId(Long branchId) {
        return companyRepository.findAllByBranch_Id(branchId);
    }

    // Get Departments by Company ID
    public List<Department> getDepartmentsByCompanyId(Long companyId) {
        return companyRepository.findAllByBranch_Company_Id(companyId);
    }

    // Get Employees by Department ID
    public List<User> getEmployeesByDepartmentId(Long departmentId) {
        return companyRepository.findAllByDepartment_Id(departmentId);
    }

    // Count Employees by Company ID
    public long countEmployeesByCompanyId(Long companyId) {
        return companyRepository.countByDepartment_Branch_Company_Id(companyId);
    }

    // Count Employees by Branch ID
    public long countEmployeesByBranchId(Long branchId) {
        return companyRepository.countByDepartment_Branch_Id(branchId);
    }

    // Save Company Photo
    private String saveImage(MultipartFile file, String name) throws IOException {
        Path uploadPath = Paths.get(uploadDir, "companyPhotos");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String filename = name.replaceAll("[^a-zA-Z0-9]", "_") + "_" + UUID.randomUUID() + fileExtension;

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return filename;
    }


}

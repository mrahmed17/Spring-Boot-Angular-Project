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
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    @Value("${upload.directory}")
    private String uploadDir;


    public Company createCompany(Company company, MultipartFile companyPhoto) {
        try {
            // Check if a company photo is provided and save it
            if (companyPhoto != null && !companyPhoto.isEmpty()) {
                String companyPhotoFilename = saveImage(companyPhoto, company.getCompanyName());
                company.setPhoto(companyPhotoFilename);
            }
            // Save the company entity in the database
            return companyRepository.save(company);

        } catch (IOException e) {
            throw new RuntimeException("Error saving company photo: " + e.getMessage(), e);
        }
    }


    private String saveImage(MultipartFile file, String fullName) throws IOException {
        // Ensure upload directory exists
        Path uploadPath = Paths.get(uploadDir, "companyPhoto");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Extract the original file name and extension
        String originalFilename = file.getOriginalFilename();
        String fileExtension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        // Generate a unique filename
        String filename = fullName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + UUID.randomUUID() + fileExtension;

        // Resolve the file path and save the file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        // Return the saved filename
        return filename;
    }



    public Company updateCompany(Long id, Company updatedCompany, MultipartFile companyPhoto) {
        Optional<Company> existingCompanyOpt = companyRepository.findById(id);

        // Check if the company exists
        if (existingCompanyOpt.isPresent()) {
            Company existingCompany = existingCompanyOpt.get();

            // Update the company name
            existingCompany.setCompanyName(updatedCompany.getCompanyName());

            try {
                // If a new photo is provided, handle the update of the photo
                if (companyPhoto != null && !companyPhoto.isEmpty()) {
                    String companyPhotoFilename = saveImage(companyPhoto, updatedCompany.getCompanyName());
                    existingCompany.setPhoto(companyPhotoFilename);
                }

                // Update branches if needed
                if (updatedCompany.getBranches() != null) {
                    existingCompany.setBranches(updatedCompany.getBranches());
                }

                // Save the updated company entity in the database
                return companyRepository.save(existingCompany);

            } catch (IOException e) {
                throw new RuntimeException("Error updating company photo: " + e.getMessage(), e);
            }

        } else {
            throw new RuntimeException("Company not found with ID: " + id);
        }
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
    public void deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        }
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

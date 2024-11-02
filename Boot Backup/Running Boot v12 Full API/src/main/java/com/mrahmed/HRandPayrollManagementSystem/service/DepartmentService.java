package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Value("${upload.directory}")
    private String uploadDir;

    public Department createDepartment(Department department, MultipartFile departmentPhoto) throws IOException {
        if (departmentPhoto != null && !departmentPhoto.isEmpty()) {
            String filename = saveImage(departmentPhoto, department.getDepartmentName());
            department.setPhoto(filename);
        }
        return departmentRepository.save(department);
    }

    private String saveImage(MultipartFile file, String departmentName) throws IOException {
        Path uploadPath = Paths.get(uploadDir, "departmentPhoto");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String filename = departmentName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + UUID.randomUUID() + fileExtension;
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        return filename;
    }

    public Department updateDepartment(Long id, Department updatedDepartment, MultipartFile departmentPhoto) throws IOException {
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (existingDepartment.isPresent()) {
            Department department = existingDepartment.get();
            department.setDepartmentName(updatedDepartment.getDepartmentName());
            department.setNumOfEmployees(updatedDepartment.getNumOfEmployees());

            if (departmentPhoto != null && !departmentPhoto.isEmpty()) {
                String filename = saveImage(departmentPhoto, updatedDepartment.getDepartmentName());
                department.setPhoto(filename);
            }

            return departmentRepository.save(department);
        } else {
            throw new RuntimeException("Department not found with id: " + id);
        }
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department findByDepartmentName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }

    public List<User> getEmployeesByDepartmentId(Long departmentId) {
        return departmentRepository.findAllEmployeesByDepartmentId(departmentId);
    }

    public long countEmployeesByDepartmentId(Long departmentId) {
        return departmentRepository.countTotalEmployeesByDepartmentId(departmentId);
    }

    public List<Department> getDepartmentsByBranchId(Long branchId) {
        return departmentRepository.findAllByBranchId(branchId);
    }

    public List<Department> getDepartmentsByCompanyId(Long companyId) {
        return departmentRepository.findAllByCompanyId(companyId);
    }

}

package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (existingDepartment.isPresent()) {
            Department department = existingDepartment.get();
            department.setDepartmentName(updatedDepartment.getDepartmentName());
            department.setNumOfEmployees(updatedDepartment.getNumOfEmployees());
            department.setPhoto(updatedDepartment.getPhoto());
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

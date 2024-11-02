package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Create a new department
    @Transactional
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Update an existing department
    @Transactional
    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
        existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());
        existingDepartment.setNumOfEmployees(updatedDepartment.getNumOfEmployees());
        return departmentRepository.save(existingDepartment);
    }

    // Get a department by ID
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Delete a department
    @Transactional
    public void deleteDepartment(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Department not found with ID: " + id);
        }
    }

    // Find all departments by branch ID
    public List<Department> getDepartmentsByBranchId(Long branchId) {
        return departmentRepository.findAllByBranch_Id(branchId);
    }

    // Count users in a department
    public long countUsersInDepartment(Long departmentId) {
        return departmentRepository.countUsersByDepartmentId(departmentId);
    }

    // Find department by name (case-insensitive)
    public Department findDepartmentByName(String departmentName) {
        return departmentRepository.findByDepartmentNameIgnoreCase(departmentName);
    }

    // Find all departments with a minimum number of employees
    public List<Department> findDepartmentsWithMinEmployees(int numOfEmployees) {
        return departmentRepository.findDepartmentsWithMinEmployees(numOfEmployees);
    }
}

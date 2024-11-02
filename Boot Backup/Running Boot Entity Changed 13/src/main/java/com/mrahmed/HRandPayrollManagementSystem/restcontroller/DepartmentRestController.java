package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;

    // Create a new department
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Department created successfully with ID: " + createdDepartment.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing department
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Department updated successfully with ID: " + updatedDepartment.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // Get a department by ID
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(department);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all departments
    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    // Delete a department
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Department deleted successfully");
        return ResponseEntity.ok(response);
    }

    // Get all departments by branch ID
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Department>> getDepartmentsByBranchId(@PathVariable Long branchId) {
        List<Department> departments = departmentService.getDepartmentsByBranchId(branchId);
        return ResponseEntity.ok(departments);
    }

    // Count users in a department
    @GetMapping("/{departmentId}/user-count")
    public ResponseEntity<Long> countUsersInDepartment(@PathVariable Long departmentId) {
        long userCount = departmentService.countUsersInDepartment(departmentId);
        return ResponseEntity.ok(userCount);
    }

    // Find a department by name
    @GetMapping("/search")
    public ResponseEntity<Department> findDepartmentByName(@RequestParam String departmentName) {
        Department department = departmentService.findDepartmentByName(departmentName);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Find departments with a minimum number of employees
    @GetMapping("/min-employees")
    public ResponseEntity<List<Department>> findDepartmentsWithMinEmployees(@RequestParam int numOfEmployees) {
        List<Department> departments = departmentService.findDepartmentsWithMinEmployees(numOfEmployees);
        return ResponseEntity.ok(departments);
    }


}


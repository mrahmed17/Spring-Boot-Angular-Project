package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public Department createDepartment(
            @RequestPart("department") Department department,
            @RequestPart(value = "departmentPhoto", required = false) MultipartFile departmentPhoto) throws IOException {
        return departmentService.createDepartment(department, departmentPhoto);
    }

    @PutMapping("/update/{id}")
    public Department updateDepartment(
            @PathVariable Long id,
            @RequestPart("department") Department updatedDepartment,
            @RequestPart(value = "departmentPhoto", required = false) MultipartFile departmentPhoto) throws IOException {
        return departmentService.updateDepartment(id, updatedDepartment, departmentPhoto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/search")
    public ResponseEntity<Department> findByDepartmentName(@RequestParam String departmentName) {
        Department department = departmentService.findByDepartmentName(departmentName);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<User>> getEmployeesByDepartmentId(@PathVariable Long id) {
        List<User> employees = departmentService.getEmployeesByDepartmentId(id);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/employees/count")
    public ResponseEntity<Long> countEmployeesByDepartmentId(@PathVariable Long id) {
        long employeeCount = departmentService.countEmployeesByDepartmentId(id);
        return ResponseEntity.ok(employeeCount);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Department>> getDepartmentsByBranchId(@PathVariable Long branchId) {
        List<Department> departments = departmentService.getDepartmentsByBranchId(branchId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Department>> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        List<Department> departments = departmentService.getDepartmentsByCompanyId(companyId);
        return ResponseEntity.ok(departments);
    }


}

package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Long> {


    // Find a department by its ID
    Department findById(long id);

    // Find all departments by branch ID
    List<Department> findAllByBranch_Id(Long branchId);

    // Count total number of users in a specific department
    @Query("SELECT COUNT(u) FROM User u WHERE u.department.id = :departmentId")
    long countUsersByDepartmentId(@Param("departmentId") Long departmentId);

    // Find a department by name (case-insensitive)
    @Query("SELECT d FROM Department d WHERE LOWER(d.departmentName) = LOWER(:departmentName)")
    Department findByDepartmentNameIgnoreCase(@Param("departmentName") String departmentName);

    // Find all departments by number of employees greater than or equal to a certain number
    @Query("SELECT d FROM Department d WHERE d.numOfEmployees >= :numOfEmployees")
    List<Department> findDepartmentsWithMinEmployees(@Param("numOfEmployees") int numOfEmployees);


}

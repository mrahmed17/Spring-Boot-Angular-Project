package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Long> {

    @Query("SELECT d FROM Department d WHERE LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :departmentName, '%'))")
    Department findByDepartmentName(@Param("departmentName") String departmentName);


    @Query("SELECT u FROM User u WHERE u.department.id = :departmentId")
    List<User> findAllEmployeesByDepartmentId(@Param("departmentId") Long departmentId);


    @Query("SELECT COUNT(u) FROM User u WHERE u.department.id = :departmentId")
    long countTotalEmployeesByDepartmentId(@Param("departmentId") Long departmentId);


    @Query("SELECT d FROM Department d WHERE d.branch.id = :branchId")
    List<Department> findAllByBranchId(@Param("branchId") Long branchId);


    @Query("SELECT d FROM Department d WHERE d.branch.company.id = :companyId")
    List<Department> findAllByCompanyId(@Param("companyId") Long companyId);


}

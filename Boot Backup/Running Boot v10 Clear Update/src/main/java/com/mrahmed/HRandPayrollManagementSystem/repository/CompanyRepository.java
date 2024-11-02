package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import com.mrahmed.HRandPayrollManagementSystem.entity.Company;
import com.mrahmed.HRandPayrollManagementSystem.entity.Department;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {

    @Query("SELECT c FROM Company c WHERE LOWER(c.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))")
    Company findByCompanyName (@Param("companyName") String companyName);

    @Query("SELECT b FROM Branch b WHERE b.company.id = :companyId")
    List<Branch> findAllByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT d FROM Department d WHERE d.branch.id = :branchId")
    List<Department> findAllDepartmentsByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT d FROM Department d WHERE d.branch.company.id = :companyId")
    List<Department> findAllDepartmentsByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT u FROM User u WHERE u.department.id = :departmentId")
    List<User> findAllEmployeesByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT COUNT(u) FROM User u WHERE u.department.branch.company.id = :companyId")
    long countTotalEmployeesByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(u) FROM User u WHERE u.department.branch.id = :branchId")
    long countTotalEmployeesByBranchId(@Param("branchId") Long branchId);


}

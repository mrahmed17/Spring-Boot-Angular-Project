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
public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b WHERE LOWER(b.branchName) LIKE LOWER(CONCAT('%', :branchName, '%'))")
    Branch findByBranchName(@Param("branchName") String branchName);

    @Query("SELECT d FROM Department d WHERE d.branch.id = :branchId")
    List<Department> findAllDepartmentsByBranchId(@Param("branchId") Long branchId);


    @Query("SELECT u FROM User u WHERE u.department.branch.id = :branchId")
    List<User> findAllEmployeesByBranchId(@Param("branchId") Long branchId);


    @Query("SELECT COUNT(d) FROM Department d WHERE d.branch.id = :branchId")
    long countTotalDepartmentsByBranchId(@Param("branchId") Long branchId);


    @Query("SELECT COUNT(u) FROM User u WHERE u.department.branch.id = :branchId")
    long countTotalEmployeesByBranchId(@Param("branchId") Long branchId);


    @Query("SELECT b FROM Branch b WHERE b.company.id = :companyId")
    List<Branch> findAllByCompanyId(@Param("companyId") Long companyId);


}

package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch findById(long id);

    // Find all branches by company ID
    List<Branch> findAllByCompany_Id(Long companyId);

    // Find branches by city
    @Query("SELECT b FROM Branch b WHERE LOWER(b.city) = LOWER(:city)")
    List<Branch> findByCityIgnoreCase(@Param("city") String city);

    // Count total number of departments in a specific branch
    @Query("SELECT COUNT(d) FROM Department d WHERE d.branch.id = :branchId")
    long countDepartmentsByBranchId(@Param("branchId") Long branchId);

    Page<Branch> findAllByCompany_Id(Long companyId, Pageable pageable);




}
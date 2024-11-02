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

    Company findByCompanyNameIgnoreCase(String companyName);

    List<Branch> findAllByCompany_Id(Long companyId);

    List<Department> findAllByBranch_Id(Long branchId);

    List<Department> findAllByBranch_Company_Id(Long companyId);

    List<User> findAllByDepartment_Id(Long departmentId);

    long countByDepartment_Branch_Company_Id(Long companyId);

    long countByDepartment_Branch_Id(Long branchId);

}

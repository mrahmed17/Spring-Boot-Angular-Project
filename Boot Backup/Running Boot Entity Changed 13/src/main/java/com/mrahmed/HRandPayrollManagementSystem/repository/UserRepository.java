package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find users with salary greater than or equal to a certain amount
    @Query("SELECT u FROM User u WHERE u.basicSalary >= :salary")
    List<User> findUsersWithSalaryGreaterThanOrEqual(@Param("salary") double salary);

    // Custom query to find users with salary less than or equal to a certain amount
    @Query("SELECT u FROM User u WHERE u.basicSalary <= :salary")
    List<User> findUsersWithSalaryLessThanOrEqual(@Param("salary") double salary);

    // Derived query to find users by name (case-insensitive)
    List<User> findByFullNameIgnoreCase(String name);

    // Derived query to find users by department ID
    List<User> findAllByDepartment_Id(Long departmentId);

    // Count users by department ID
    long countByDepartment_Id(Long departmentId);

    // Paginated query to find users by department ID
    Page<User> findAllByDepartment_Id(Long departmentId, Pageable pageable);

    // Custom query to find users by salary and department ID
    @Query("SELECT u FROM User u WHERE u.basicSalary > :salary AND u.department.id = :departmentId")
    List<User> findUsersBySalaryAndDepartment(@Param("salary") double salary, @Param("departmentId") Long departmentId);


}

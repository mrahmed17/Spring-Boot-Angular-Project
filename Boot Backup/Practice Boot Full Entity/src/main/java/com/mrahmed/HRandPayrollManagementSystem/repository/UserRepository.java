package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Role;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Finding Users by Email
    Optional<User> findByEmail(String email);

    // Finding Users with Specific Conditions
    @Query("SELECT u FROM User u WHERE u.basicSalary >= :salary")
    List<User> findUsersWithSalaryGreaterThanOrEqual(@Param("salary") BigDecimal salary);

    // Finding Users with Salary Less Than or Equal To
    @Query("SELECT u FROM User u WHERE u.basicSalary <= :salary")
    List<User> findUsersWithSalaryLessThanOrEqual(@Param("salary") BigDecimal salary);

    // Find Users by Role
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(Role role);

    // Search User record by User FullName or part of the name
    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findUserByUserNamePart(@Param("name") String name);

    // Search User record by User Gender
    @Query("SELECT u FROM User u WHERE u.gender = :gender")
    List<User> findUserByGender(@Param("gender") String gender);

    // Search User record by User Joined Date
    @Query("SELECT u FROM User u WHERE u.joinedDate = :joinedDate")
    List<User> findUserByJoinedDate(@Param("joinedDate") LocalDate joinedDate);

}

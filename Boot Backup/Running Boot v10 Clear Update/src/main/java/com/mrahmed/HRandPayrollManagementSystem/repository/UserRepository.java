package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Role;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.basicSalary >= :salary")
    List<User> findUsersWithSalaryGreaterThanOrEqual(@Param("salary") double salary);

    @Query("SELECT u FROM User u WHERE u.basicSalary <= :salary")
    List<User> findUsersWithSalaryLessThanOrEqual(@Param("salary") double salary);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") Role role);

    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByFullNameContaining(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.gender = :gender")
    List<User> findByGender(@Param("gender") String gender);

    @Query("SELECT u FROM User u WHERE u.joinedDate = :joinedDate")
    List<User> findByJoinedDate(@Param("joinedDate") LocalDate joinedDate);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :userId")
    void deleteById(@Param("userId") Long userId);



}

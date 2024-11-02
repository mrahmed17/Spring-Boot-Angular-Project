package com.mrahmed.HRandPayrollManagementSystem.restcontroller;


import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserRestController {

    @Autowired
    private UserService userService;

    // Create user with optional profile photo
    @PostMapping("/create")
    public ResponseEntity<String> createUser(
            @RequestPart("user") User user,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto
    ) {
        try {
            userService.saveUser(user, profilePhoto);
            return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload profile photo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update user with optional profile photo
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long id,
            @RequestPart("user") User user,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto
    ) {
        try {
            userService.updateUser(id, user, profilePhoto);
            return new ResponseEntity<>("User updated successfully.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Find user by ID
    @GetMapping("/find/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("User not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    // Get users with salary greater than or equal to the specified value
    @GetMapping("/salary/greaterThanOrEqual/{salary}")
    public ResponseEntity<List<User>> getUsersWithSalaryGreaterThanOrEqual(@PathVariable("salary") double salary) {
        List<User> users = userService.getUsersWithSalaryGreaterThanOrEqual(salary);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get users with salary less than or equal to the specified value
    @GetMapping("/salary/lessThanOrEqual/{salary}")
    public ResponseEntity<List<User>> getUsersWithSalaryLessThanOrEqual(@PathVariable("salary") double salary) {
        List<User> users = userService.getUsersWithSalaryLessThanOrEqual(salary);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get users by department ID
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<User>> getUsersByDepartment(@PathVariable("departmentId") Long departmentId) {
        List<User> users = userService.getUsersByDepartment(departmentId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get paginated users by department ID
    @GetMapping("/department/{departmentId}/page")
    public ResponseEntity<Page<User>> getUsersByDepartmentPaginated(
            @PathVariable("departmentId") Long departmentId, Pageable pageable) {
        Page<User> usersPage = userService.getUsersByDepartmentPaginated(departmentId, pageable);
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }

    // Get users by full name (case-insensitive search)
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<User>> getUsersByFullName(@PathVariable("name") String name) {
        List<User> users = userService.findUsersByFullName(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get users by salary and department
    @GetMapping("/salaryAndDepartment/{salary}/{departmentId}")
    public ResponseEntity<List<User>> getUsersBySalaryAndDepartment(
            @PathVariable("salary") double salary,
            @PathVariable("departmentId") Long departmentId
    ) {
        List<User> users = userService.getUsersBySalaryAndDepartment(salary, departmentId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Count users by department
    @GetMapping("/department/{departmentId}/count")
    public ResponseEntity<Long> countUsersByDepartment(@PathVariable("departmentId") Long departmentId) {
        long count = userService.countUsersByDepartment(departmentId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}


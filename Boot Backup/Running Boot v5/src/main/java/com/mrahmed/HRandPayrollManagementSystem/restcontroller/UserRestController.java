package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Role;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    // Get User by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(id));
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User updatedUser) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            User user = userService.updateUser(id, updatedUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get User by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        Optional<User> userOptional = userService.getUserByEmail(email);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Get Users with Salary Greater Than or Equal
    @GetMapping("/salary/greaterThanOrEqual/{salary}")
    public ResponseEntity<List<User>> getUsersWithSalaryGreaterThanOrEqual(@PathVariable("salary") BigDecimal salary) {
        List<User> users = userService.getUsersWithSalaryGreaterThanOrEqual(salary);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    // Get Users with Salary Less Than or Equal
    @GetMapping("/salary/lessThanOrEqual/{salary}")
    public ResponseEntity<List<User>> getUsersWithSalaryLessThanOrEqual(@PathVariable("salary") BigDecimal salary) {
        List<User> users = userService.getUsersWithSalaryLessThanOrEqual(salary);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    // Get Users by Role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable("role") Role role) {
        List<User> users = userService.getUsersByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Search Users by Partial Name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<User>> getUsersByFullNamePart(@PathVariable("name") String name) {
        List<User> users = userService.getUsersByFullNamePart(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Search Users by Gender
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<User>> getUsersByGender(@PathVariable("gender") String gender) {
        List<User> users = userService.getUsersByGender(gender);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Search Users by Joined Date
    @GetMapping("/joinedDate/{joinedDate}")
    public ResponseEntity<List<User>> getUsersByJoinedDate(@PathVariable("joinedDate") LocalDate joinedDate) {
        List<User> users = userService.getUsersByJoinedDate(joinedDate);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }





}




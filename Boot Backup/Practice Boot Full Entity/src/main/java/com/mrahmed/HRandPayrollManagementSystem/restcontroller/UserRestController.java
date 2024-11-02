package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrahmed.HRandPayrollManagementSystem.entity.Role;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserRestController {

    @Autowired
    private UserService userService;


    @PostMapping("/save")
    public ResponseEntity<User> saveUser(
            @RequestPart(value = "static/images/user") User user,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto
    ) throws IOException {

        // Save the user along with the profile image
        userService.saveUser(user, profilePhoto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


//    @PostMapping("/save")
//    public ResponseEntity<String> saveUser(
//            @RequestParam(value = "user") String userJson,
//            @RequestParam(value = "profilePhoto") MultipartFile profilePhoto
//    ) throws IOException {
//
//       ObjectMapper objectMapper =new ObjectMapper();
//       User user = objectMapper.readValue(userJson,User.class);
//
//        try {
//            userService.saveUser(user, profilePhoto);
//            return new ResponseEntity<>("User added successfully with image.", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to add User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") long id,
            @RequestBody User user,
            @RequestParam(value = "profilePhoto") MultipartFile profilePhoto
    ) throws IOException {
        userService.updateUserById(id, user, profilePhoto);
        return ResponseEntity.ok(user);
    }


//    @PutMapping("/update/{id}")
//    public void updateUser(@PathVariable ("id") Long id, @RequestBody User userDetails) {
//        userService.updateUserById(id, userDetails);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @DeleteMapping("/delete/{id}")
//    public void deleteUser(@PathVariable ("id") Long id) {
//        userService.deleteUserById(id);
//    }


    @GetMapping("/view/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


//    @GetMapping("/view/{id}")
//    public User getUserById(@PathVariable ("id") Long id) {
//        return userService.getUserById(id);
//    }


    @GetMapping("/findByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findBySalaryGreaterThanOrEqual")
    public ResponseEntity<List<User>> findUsersWithSalaryGreaterThanOrEqual(@RequestParam("salary") BigDecimal salary) {
        List<User> users = userService.findUsersWithSalaryGreaterThanOrEqual(salary);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findBySalaryLessThanOrEqual")
    public ResponseEntity<List<User>> findUsersWithSalaryLessThanOrEqual(@RequestParam("salary") BigDecimal salary) {
        List<User> users = userService.findUsersWithSalaryLessThanOrEqual(salary);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }


//    @GetMapping("/findByRole")
//    public ResponseEntity<List<User>> findUsersByRole(@RequestParam("role") Role role) {
//        List<User> users = userService.findUsersByRole(role);
//        if (users.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return ResponseEntity.ok(users);
//    }

    @GetMapping("/findByRole")
    public ResponseEntity<List<User>> findUsersByRole(@RequestParam("role") String role) {
        try {
            Role roleEnum = Role.valueOf(role.toUpperCase());
            List<User> users = userService.findUsersByRole(roleEnum);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Search User by Full Name or part of the name
    @GetMapping("/searchByName")
    public ResponseEntity<List<User>> findUserByName(@RequestParam("name") String name) {
        List<User> users = userService.findUserByUserNamePart(name);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Search User by Gender
    @GetMapping("/searchByGender")
    public ResponseEntity<List<User>> findUserByGender(@RequestParam("gender") String gender) {
        List<User> users = userService.findUserByGender(gender);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Search User by Joined Date
    @GetMapping("/searchByJoinedDate")
    public ResponseEntity<List<User>> findUserByJoinedDate(@RequestParam("joinedDate") String joinedDate) {
        try {
            // Convert the joinedDate parameter from String to LocalDate
            LocalDate parsedDate = LocalDate.parse(joinedDate);
            List<User> users = userService.findUserByJoinedDate(parsedDate);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            // Return a bad request response if the date is not in the correct format
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}

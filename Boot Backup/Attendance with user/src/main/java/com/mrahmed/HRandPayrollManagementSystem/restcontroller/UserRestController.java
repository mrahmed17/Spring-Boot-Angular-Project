package com.mrahmed.HRandPayrollManagementSystem.restcontroller;

import com.mrahmed.HRandPayrollManagementSystem.entity.Attendance;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserRestController {


    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user = userService.getAllUsers();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(user);
    }


//    @GetMapping("/")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }


    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }


//    @PostMapping("/save")
//    public void saveUser(@RequestBody User user) {
//        userService.saveUser(user);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping("/delete/{id}")
//    public void deleteUser(@PathVariable ("id") Long id) {
//        userService.deleteUserById(id);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") long id, @RequestBody User userDetails) {
        userService.updateUserById(id, userDetails);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/update/{id}")
//    public void updateUser(@PathVariable ("id") Long id, @RequestBody User userDetails) {
//        userService.updateUserById(id, userDetails);
//    }


    @GetMapping("/view/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


//    @GetMapping("/view/{id}")
//    public User getUserById(@PathVariable ("id") Long id) {
//        return userService.getUserById(id);
//    }


}

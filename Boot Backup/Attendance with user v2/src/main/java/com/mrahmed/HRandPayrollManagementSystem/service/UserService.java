package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.exceptionhandle.GlobalExceptionHandler;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

//      public void saveUser(User user) {
//        userRepository.save(user);
//    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Couldn't find user' by this id: " + id));
    }

    public void deleteUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }

//    public void deleteUserById(long id) {
//        userRepository.deleteById(id);
//    }

    public void updateUserById(long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.UserNotFoundException("User not found"));
        existingUser.setFullName(userDetails.getFullName());
        userRepository.save(existingUser);
    }

//    public void updateUserById(long id, User user) {
//        userRepository.save(user);
//    }
//




}

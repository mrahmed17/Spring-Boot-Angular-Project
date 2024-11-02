package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Role;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.exceptionhandle.GlobalExceptionHandler;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return  userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new GlobalExceptionHandler.UserNotFoundException("Couldn't find user' by this id: " + id));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User updateUser(long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Update the fields of the existing user with values from the updatedUser
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword()); // Password encryption logic can go here
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            existingUser.setNationalId(updatedUser.getNationalId());
            existingUser.setContact(updatedUser.getContact());
            existingUser.setBasicSalary(updatedUser.getBasicSalary());
            existingUser.setJoinedDate(updatedUser.getJoinedDate());
            existingUser.setProfilePhoto(updatedUser.getProfilePhoto());
            existingUser.setActive(updatedUser.isActive());
            existingUser.setRole(updatedUser.getRole());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }


    public void deleteUser(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }

//    public void deleteUser(long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new GlobalExceptionHandler.UserNotFoundException("User not found by this id: " + id));
//        userRepository.deleteById(id);
//    }

    // Get Users with Salary Greater Than or Equal to a Value
    public List<User> getUsersWithSalaryGreaterThanOrEqual(BigDecimal salary) {
        return userRepository.findUsersWithSalaryGreaterThanOrEqual(salary);
    }

    // Get Users with Salary Less Than or Equal to a Value
    public List<User> getUsersWithSalaryLessThanOrEqual(BigDecimal salary) {
        return userRepository.findUsersWithSalaryLessThanOrEqual(salary);
    }

    // Get Users by Role
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    // Search Users by Partial Name
    public List<User> getUsersByFullNamePart(String name) {
        return userRepository.findUserByUserNamePart(name);
    }

    // Search Users by Gender
    public List<User> getUsersByGender(String gender) {
        return userRepository.findUserByGender(gender);
    }

    // Search Users by Joined Date
    public List<User> getUsersByJoinedDate(LocalDate joinedDate) {
        return userRepository.findUserByJoinedDate(joinedDate);
    }

}

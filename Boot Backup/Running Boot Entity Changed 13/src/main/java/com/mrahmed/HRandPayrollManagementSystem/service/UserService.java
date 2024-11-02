package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Value("${upload.directory}")
    private String uploadDir;

    // Save a new user with an optional profile photo
    @Transactional
    public void saveUser(User user, MultipartFile profilePhoto) throws IOException {
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String profilePhotoFilename = saveImage(profilePhoto, user.getFullName());
            user.setProfilePhoto(profilePhotoFilename);
        }
        userRepository.save(user);
    }

    // Helper method to save the profile photo to the file system
    private String saveImage(MultipartFile file, String fullName) throws IOException {
        Path uploadPath = Paths.get(uploadDir, "profilePhotos");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFilename = file.getOriginalFilename();
        String fileExtension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String filename = fullName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + UUID.randomUUID() + fileExtension;
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        return filename;
    }

    // Update user details and handle profile photo update
    @Transactional
    public void updateUser(Long id, User updatedUser, MultipartFile profilePhoto) throws IOException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Update basic user details
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setBasicSalary(updatedUser.getBasicSalary());

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String profilePhotoFilename = saveImage(profilePhoto, updatedUser.getFullName());
            existingUser.setProfilePhoto(profilePhotoFilename);
        }

        userRepository.save(existingUser);
    }

    // Find user by ID
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Find all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete user by ID
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // Find users by salary greater than or equal to the specified amount
    public List<User> getUsersWithSalaryGreaterThanOrEqual(double salary) {
        return userRepository.findUsersWithSalaryGreaterThanOrEqual(salary);
    }

    // Find users by salary less than or equal to the specified amount
    public List<User> getUsersWithSalaryLessThanOrEqual(double salary) {
        return userRepository.findUsersWithSalaryLessThanOrEqual(salary);
    }

    // Find users by department ID
    public List<User> getUsersByDepartment(Long departmentId) {
        return userRepository.findAllByDepartment_Id(departmentId);
    }

    // Count users by department ID
    public long countUsersByDepartment(Long departmentId) {
        return userRepository.countByDepartment_Id(departmentId);
    }

    // Paginated query to find users by department ID
    public Page<User> getUsersByDepartmentPaginated(Long departmentId, Pageable pageable) {
        return userRepository.findAllByDepartment_Id(departmentId, pageable);
    }

    // Find users by name (case-insensitive)
    public List<User> findUsersByFullName(String name) {
        return userRepository.findByFullNameIgnoreCase(name);
    }

    // Find users by salary and department ID
    public List<User> getUsersBySalaryAndDepartment(double salary, Long departmentId) {
        return userRepository.findUsersBySalaryAndDepartment(salary, departmentId);
    }
}

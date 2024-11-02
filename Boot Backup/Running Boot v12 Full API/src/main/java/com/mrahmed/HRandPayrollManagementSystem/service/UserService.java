package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Role;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.repository.AttendanceRepository;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Value("${upload.directory}")
    private String uploadDir;


    @Transactional
    public void  saveUser(User user, MultipartFile profilePhoto) throws IOException {
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String profilePhotoFilename = saveImage(profilePhoto, user.getFullName());
            user.setProfilePhoto(profilePhotoFilename);
        }
        if (!user.isActive()) {
            user.setActive(true);
        }
         userRepository.save(user);
    }

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Transactional
    public void updateUser(Long id, User updatedUser, MultipartFile profilePhoto) throws IOException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setNationalId(updatedUser.getNationalId());
        existingUser.setContact(updatedUser.getContact());
        existingUser.setBasicSalary(updatedUser.getBasicSalary());
        existingUser.setRole(updatedUser.getRole());

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String profilePhotoFilename = saveImage(profilePhoto, updatedUser.getFullName());
            existingUser.setProfilePhoto(profilePhotoFilename);
        }

        userRepository.save(existingUser);
    }

    public void deleteUserById(Long id) {
        attendanceRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersWithSalaryGreaterThanOrEqual(double salary) {
        return userRepository.findUsersWithSalaryGreaterThanOrEqual(salary);
    }

    public List<User> getUsersWithSalaryLessThanOrEqual(double salary) {
        return userRepository.findUsersWithSalaryLessThanOrEqual(salary);
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> getUsersByFullNamePart(String fullNamePart) {
        return userRepository.findByFullNameContaining(fullNamePart);
    }

    public List<User> getUsersByGender(String gender) {
        return userRepository.findByGender(gender);
    }

    public List<User> getUsersByJoinedDate(LocalDate joinedDate) {
        return userRepository.findByJoinedDate(joinedDate);
    }
}

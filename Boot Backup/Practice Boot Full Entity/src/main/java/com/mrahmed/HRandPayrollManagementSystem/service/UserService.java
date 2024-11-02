package com.mrahmed.HRandPayrollManagementSystem.service;

import com.mrahmed.HRandPayrollManagementSystem.entity.Role;
import com.mrahmed.HRandPayrollManagementSystem.entity.User;
import com.mrahmed.HRandPayrollManagementSystem.exceptionhandle.GlobalExceptionHandler;
import com.mrahmed.HRandPayrollManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
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

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.UserNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void saveUser(User user, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFilename = saveImage(imageFile, user);
            user.setProfilePhoto(imageFilename);
        }
        userRepository.save(user);
    }

    private String saveImage(MultipartFile file, User user) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/static/images/user");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Extract the file extension from the original filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        // Create a unique filename for the image
        String filename = user.getFullName() + "_" + UUID.randomUUID().toString() + fileExtension;
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        return filename;
    }


    @Transactional
    public void updateUserById(long id, User updatedUser, MultipartFile profilePhoto)
            throws IOException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setNationalId(updatedUser.getNationalId());
        existingUser.setContact(updatedUser.getContact());
        existingUser.setBasicSalary(updatedUser.getBasicSalary());
        existingUser.setRole(updatedUser.getRole());

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String imageFilename = saveImage(profilePhoto, existingUser);
            existingUser.setProfilePhoto(imageFilename);
        }
        userRepository.save(existingUser);
    }


//    public void updateUserById(long id, User user) {
//        userRepository.save(user);
//    }

public void deleteUser(long id) {
    userRepository.deleteById(id);
}

//    public void deleteUserById(long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        userRepository.deleteById(id);
//    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findUsersWithSalaryGreaterThanOrEqual(BigDecimal salary) {
        return userRepository.findUsersWithSalaryGreaterThanOrEqual(salary);
    }

    public List<User> findUsersWithSalaryLessThanOrEqual(BigDecimal salary) {
        return userRepository.findUsersWithSalaryLessThanOrEqual(salary);
    }

    public List<User> findUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }


    public List<User> findUserByUserNamePart(String name) {
        return userRepository.findUserByUserNamePart(name);
    }

    public List<User> findUserByGender(String gender) {
        return userRepository.findUserByGender(gender);
    }

    public List<User> findUserByJoinedDate(LocalDate joinedDate) {
        return userRepository.findUserByJoinedDate(joinedDate);
    }


}

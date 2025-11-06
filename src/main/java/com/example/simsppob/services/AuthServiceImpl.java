package com.example.simsppob.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.simsppob.dto.LoginRequest;
import com.example.simsppob.dto.ProfileResponse;
import com.example.simsppob.dto.RegistrationRequest;
import com.example.simsppob.dto.UpdateProfileRequest;
import com.example.simsppob.jwt.JwtUtil;
import com.example.simsppob.models.User;
import com.example.simsppob.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public void register(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar");
        }

        userRepository.save(User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .balance(0L)
                .build());
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Username atau password salah"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Username atau password salah");
        }
        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    public ProfileResponse profile() {
        String email = jwtUtil.getEmailFromCurrentRequest();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        return toProfileResponse(user);
    }

    @Override
    public ProfileResponse editProfile(UpdateProfileRequest request) {
        String email = jwtUtil.getEmailFromCurrentRequest();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);
        return toProfileResponse(user);
    }

    @Override
    public ProfileResponse editProfileImage(MultipartFile file) {
        String email = jwtUtil.getEmailFromCurrentRequest();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        validateImageFile(file);
        try {
            // Generate unique filename
            String fileName = generateUniqueFileName(file, email);

            // Create upload directory if not exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Generate file URL
            String fileUrl = baseUrl + "/" + uploadDir + "/" + fileName;

            // Update user profile image
            user.setProfileImage(fileUrl);
            userRepository.save(user);

            return toProfileResponse(user);

        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage());
        }
    }

    private ProfileResponse toProfileResponse(User user) {
        return ProfileResponse.builder()
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();
    }

    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File tidak boleh kosong");
        }

        // Check content type
        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/jpeg") &&
                        !contentType.equals("image/jpg") &&
                        !contentType.equals("image/png"))) {
            throw new RuntimeException("Format Image tidak sesuai. Hanya format JPEG dan PNG yang diizinkan");
        }

        // Check file extension
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
                    .toLowerCase();
            if (!fileExtension.equals("jpg") &&
                    !fileExtension.equals("jpeg") &&
                    !fileExtension.equals("png")) {
                throw new RuntimeException("Format Image tidak sesuai. Hanya format JPEG dan PNG yang diizinkan");
            }
        }

        // Check file size (max 2MB)
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new RuntimeException("Ukuran file terlalu besar. Maksimal 2MB");
        }
    }

    private String generateUniqueFileName(MultipartFile file, String email) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String cleanEmail = email.replaceAll("[^a-zA-Z0-9]", "");

        return cleanEmail + "_" + timeStamp + "." + fileExtension;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "jpg";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "jpg";
    }
}
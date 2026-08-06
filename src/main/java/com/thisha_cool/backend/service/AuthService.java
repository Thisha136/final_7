package com.thisha_cool.backend.service;
import com.thisha_cool.backend.security.JWTServices;
import com.thisha_cool.backend.dto.RegisterRequest;
import com.thisha_cool.backend.entity.User;
import com.thisha_cool.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.thisha_cool.backend.dto.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTServices jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return "User not found";
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid Password";
        }

        return jwtService.generateToken(user.getEmail());
    }
    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }
}
package com.spendsense.backend.service;

import com.spendsense.backend.dto.LoginRequest;
import com.spendsense.backend.dto.RegisterRequest;
import com.spendsense.backend.model.User;
import com.spendsense.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // Bean pe depend nahi — seedha object banao
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, Object> register(RegisterRequest req) {
        Map<String, Object> response = new HashMap<>();

        // Email already exists?
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            response.put("error", "Email already registered");
            return response;
        }

        // Naya user banao
        User user = new User();
        user.setName(req.getName().trim());
        user.setEmail(req.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);

        response.put("message", "Account created successfully");
        return response;
    }

    public Map<String, Object> login(LoginRequest req) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> userOpt = userRepository.findByEmail(
                req.getEmail().trim().toLowerCase()
        );

        // User nahi mila
        if (userOpt.isEmpty()) {
            response.put("error", "Invalid email or password");
            return response;
        }

        User user = userOpt.get();

        // Password check
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            response.put("error", "Invalid email or password");
            return response;
        }

        // Token banao
        String token = "spendsense-" + user.getId()
                + "-" + System.currentTimeMillis();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id",    user.getId());
        userInfo.put("name",  user.getName());
        userInfo.put("email", user.getEmail());

        response.put("token", token);
        response.put("user",  userInfo);
        return response;
    }
}

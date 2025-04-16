package com.example.authservice.service;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public void registerUser(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Username already exists.");
        }
        User savedUser = new User();
        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(savedUser);
    }
    
    public boolean authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            return false;
        }
        return true;
    }
}

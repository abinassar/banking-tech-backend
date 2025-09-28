package com.cursos.springsecuritycourse.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursos.springsecuritycourse.dto.UserRegistrationRequest;
import com.cursos.springsecuritycourse.entity.User;
import com.cursos.springsecuritycourse.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getEmail())) {
            throw new IllegalArgumentException("USER_ALREADY_EXIST");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("USER_ALREADY_EXIST");
        }

        User user = new User();
        user.setUsername(request.getEmail());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("USER_DOES_NOT_EXIST");
        }

        User user = optionalUser.get();
        return user;
    }

    public User updateUser(UserRegistrationRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("USER_DOES_NOT_EXIST");
        }

        User user = optionalUser.get();
        return userRepository.save(user);

    }

    public User updateUserLoginTime(User user) {

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            return null;
        }

        User userFound = optionalUser.get();
        return userRepository.save(userFound);

    }

}

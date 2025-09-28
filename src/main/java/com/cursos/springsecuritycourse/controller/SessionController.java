package com.cursos.springsecuritycourse.controller;

import com.cursos.springsecuritycourse.entity.User;
import com.cursos.springsecuritycourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public List<User> getAllSessions() {
        return userRepository.findAll();
    }
}

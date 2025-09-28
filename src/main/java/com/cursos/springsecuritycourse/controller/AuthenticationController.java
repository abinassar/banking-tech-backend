package com.cursos.springsecuritycourse.controller;

import com.cursos.springsecuritycourse.dto.AuthenticationRequest;
import com.cursos.springsecuritycourse.dto.AuthenticationResponse;
import com.cursos.springsecuritycourse.dto.UserRegistrationRequest;
import com.cursos.springsecuritycourse.entity.User;
import com.cursos.springsecuritycourse.service.AuthenticationService;
import com.cursos.springsecuritycourse.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest authRequest){
        AuthenticationResponse jwtDto = authenticationService.login(authRequest);
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationRequest user) {
        User userCreated = userService.registerNewUser(user);
        return ResponseEntity.ok(userCreated);
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserRegistrationRequest user) {
        User userCreated = userService.updateUser(user);
        return ResponseEntity.ok(userCreated);
    }

}

package com.cursos.springsecuritycourse.service;

import com.cursos.springsecuritycourse.dto.AuthenticationRequest;
import com.cursos.springsecuritycourse.dto.AuthenticationResponse;
import com.cursos.springsecuritycourse.entity.User;
import com.cursos.springsecuritycourse.entity.UserSession;
import com.cursos.springsecuritycourse.repository.UserRepository;
import com.cursos.springsecuritycourse.repository.UserSessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSessionRepository userSessionRepository;

    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        try {
            
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            );

            authenticationManager.authenticate(authToken);

        } catch (Exception e) {
            
            throw new IllegalArgumentException(e.getMessage());

        }

        User user = userRepository.findByUsername(authRequest.getUsername()).get();

        // Update last login
        
        user.setLastLogin(LocalDateTime.now());
        userService.updateUserLoginTime(user);

        // Save user session

        UserSession session = new UserSession(user.getUsername(), user.getName(), user.getEmail(), LocalDateTime.now(), user.getRegistrationDate());
        userSessionRepository.save(session);

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("email", user.getEmail());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }
}

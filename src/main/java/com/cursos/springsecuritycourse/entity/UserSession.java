package com.cursos.springsecuritycourse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String email;

    private LocalDateTime registrationDate;
    private LocalDateTime loginTime;

    // Constructors
    public UserSession() {}

    public UserSession(String username, 
                       String name,
                       String email, 
                       LocalDateTime loginTime,
                       LocalDateTime registrationDate) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.loginTime = loginTime;
        this.registrationDate = registrationDate;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
}


package com.cursos.springsecuritycourse.dto;

import com.cursos.springsecuritycourse.util.Role;

public class UserRegistrationRequest {

    private String name;
    private String email;
    private String password;
    private Role role;
    private String tasks;
    private String countries;

    public UserRegistrationRequest(String name,
                                   String email,
                                   String password,
                                   String tasks,
                                   String countries,
                                   Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.tasks = tasks;
        this.countries = countries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

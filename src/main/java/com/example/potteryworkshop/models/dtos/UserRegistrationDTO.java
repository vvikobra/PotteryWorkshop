package com.example.potteryworkshop.models.dtos;

import com.example.potteryworkshop.util.validation.UniqueEmail;
import jakarta.validation.constraints.*;

public class UserRegistrationDTO {

    private String name;

    private String email;

    private String password;

    private String confirmPassword;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @NotEmpty(message = "User name cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    @NotEmpty(message = "Email cannot be null or empty!")
    @Email
    @UniqueEmail
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password cannot be null or empty!")
    @Size(min = 8, max = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Confirm Password cannot be null or empty!")
    @Size(min = 8, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "username='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}

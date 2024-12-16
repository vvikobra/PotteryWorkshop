package com.example.potteryworkshop.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserInputDTO {
    private String password;
    private String confirmPassword;
    private String name;
    private String email;
    private String roleName;

    public UserInputDTO(String password, String confirmPassword, String name, String email) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.email = email;
    }

    public UserInputDTO() {
    }

    @NotNull
    @Length(min = 8, message = "Password cannot be less than eight characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Length(min = 8, message = "Password cannot be less than eight characters")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull
    @Length(min = 3, message = "User name must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Email
    @Length(min = 5, message = "The email is too short")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

package com.app.manager.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateUser {
    @NotEmpty(message = "Please enter firstName")
    private String firstName;

    @NotEmpty(message = "Please enter lastName")
    private String lastName;

    @NotEmpty(message = "Please enter email")
    @Email
    private String email;

    @NotEmpty(message = "Please enter phone")
    private String phone;

    @NotEmpty(message = "Please enter gender")
    private String gender;

    @NotEmpty(message = "Please enter dateOfBirth")
    private String dateOfBirth;

    @NotEmpty(message = "Please enter nationality")
    private String nationality;
}

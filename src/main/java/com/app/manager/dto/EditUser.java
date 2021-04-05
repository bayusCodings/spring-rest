package com.app.manager.dto;

import lombok.Data;

@Data
public class EditUser {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String dateOfBirth;
    private String nationality;
    private String blocked;
}

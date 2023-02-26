package com.ielts.assistance.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> role;
    private String phoneNumber;
    private Date dob;
    private String fullName;
    private String createBy;
    private String modifyBy;
    @NotBlank
    private String password;
}
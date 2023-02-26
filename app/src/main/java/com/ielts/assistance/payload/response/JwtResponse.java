package com.ielts.assistance.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private String username;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private String fullName;
    private List<String> roles;

    public JwtResponse(String token, UUID id, String username, String email, String phoneNumber, Date dateOfBirth, String fullName, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.fullName = fullName;
        this.roles = roles;
    }
}

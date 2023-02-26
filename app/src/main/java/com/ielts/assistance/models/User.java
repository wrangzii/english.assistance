package com.ielts.assistance.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID userId;
    private String username;
    @NotEmpty(message = "*Please provide full name")
    private String fullName;
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
    private String phoneNumber;
    @ManyToOne()
    @JoinColumn(name = "plan_id")
    private UserPlan planId;
    private Date dob;
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    private Boolean isActive;
    private String resetPasswordToken;
    private Date createDate;
    private String createBy;
    private Date modifyDate;
    private String modifyBy;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Provider provider;

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(String email, String username, String fullName, String phoneNumber, Date dob, String password) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.password = password;
        this.email = email;
    }
}

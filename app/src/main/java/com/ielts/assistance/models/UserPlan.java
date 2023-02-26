package com.ielts.assistance.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_plan")
public class UserPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;
    private String planType;
    private String description;
}

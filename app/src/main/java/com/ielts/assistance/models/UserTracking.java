package com.ielts.assistance.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_tracking")
public class UserTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long track_id;
}

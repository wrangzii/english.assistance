package com.ielts.assistance.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "essays")
public class Essays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long essayId;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions questionId;
    private String essayType;
    private String description;

}

package com.ielts.assistance.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @ManyToOne()
    @JoinColumn(name = "topic_id")
    private Topics topicId;
    private String question;
}

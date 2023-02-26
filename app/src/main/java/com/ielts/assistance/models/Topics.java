package com.ielts.assistance.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "topics")
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;
    private String topic;

}

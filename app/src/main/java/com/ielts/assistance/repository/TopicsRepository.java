package com.ielts.assistance.repository;

import com.ielts.assistance.models.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {
    Boolean existsByTopic(String name);
}

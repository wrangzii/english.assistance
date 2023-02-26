package com.ielts.assistance.service;

import com.ielts.assistance.models.Topics;
import com.ielts.assistance.payload.request.TopicRequest;
import com.ielts.assistance.payload.response.DataResponse;
import com.ielts.assistance.payload.response.ResponseObject;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {
    DataResponse getAllTopic(Integer pageNumber);
//    List<Topics> searchSpecification(Integer pageNumber, Specification<Topics> spec);
    ResponseEntity<ResponseObject> getTopicById(Long id);
    ResponseEntity<ResponseObject> addTopic(TopicRequest topic);
    ResponseEntity<ResponseObject> deleteTopic(Long id);
    ResponseEntity<ResponseObject> editTopic(TopicRequest Major, Long id);

}

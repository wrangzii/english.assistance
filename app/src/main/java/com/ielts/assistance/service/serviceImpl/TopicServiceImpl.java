package com.ielts.assistance.service.serviceImpl;

import com.ielts.assistance.models.Topics;
import com.ielts.assistance.payload.request.TopicRequest;
import com.ielts.assistance.payload.response.DataResponse;
import com.ielts.assistance.payload.response.ResponseObject;
import com.ielts.assistance.repository.TopicsRepository;
import com.ielts.assistance.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TopicServiceImpl implements TopicService {
    private final TopicsRepository topicRepo;

    @Override
    public DataResponse getAllTopic(Integer pageNumber) {
        int pageSize = 10;
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<Topics> pagedResult = topicRepo.findAll(paging);
        DataResponse data = new DataResponse();
        if(pagedResult.hasContent()) {
            data.setData(pagedResult.getContent());
            data.setPageNumber(pagedResult.getTotalPages());
            return data;
        } else {
            return data;
        }
    }

//    @Override
//    public List<Topics> searchMajorSpecification(Integer pageNumber, Specification<Major> spec) {
//        int pageSize = 15;
//        Pageable paging = PageRequest.of(pageNumber, pageSize);
//        Page<Major> pagedResult = majorRepo.findAll(spec, paging);
//        if(pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//            return new ArrayList<>();
//        }
//    }

    @Override
    public ResponseEntity<ResponseObject> getTopicById(Long id) {
        try {
            Optional<Topics> checkExisted = topicRepo.findById(id);
            if (checkExisted.isPresent()) {
                return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(),"Get t successfully!",checkExisted));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Error: Topic name is already taken!"));
    }

    @Override
    public ResponseEntity<ResponseObject> addTopic(TopicRequest topicRequest) {
        try {
            Boolean checkExisted = topicRepo.existsByTopic(topicRequest.getTopicName());
            Topics topic = new Topics();
            if (!checkExisted) {
                topic.setTopic(topicRequest.getTopicName());
                topicRepo.save(topic);
                return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(), "Add topic successfully!", topic));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(),"Error:  name is already taken!"));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteTopic(Long id) {
        try {
            Optional<Topics> deleteTopic = topicRepo.findById(id);
            if (deleteTopic.isPresent()) {
                topicRepo.deleteById(id);
                return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(), "Delete topic successfully!"));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(),"Error: topic name is not exist!"));
    }

    @Override
    public ResponseEntity<ResponseObject> editTopic(TopicRequest topicRequest, Long id) {
        try {
            Optional<Topics> editTopic = topicRepo.findById(id);
            if (editTopic.isPresent()) {
                editTopic.get().setTopic(topicRequest.getTopicName());
                topicRepo.save(editTopic.get());
                return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(), "Edit topic successfully!", editTopic));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(),"Error: topic is not exist!"));
    }
}


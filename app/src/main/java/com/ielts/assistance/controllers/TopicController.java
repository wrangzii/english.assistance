package com.ielts.assistance.controllers;

import com.ielts.assistance.payload.request.TopicRequest;
import com.ielts.assistance.payload.response.DataResponse;
import com.ielts.assistance.payload.response.ResponseObject;
import com.ielts.assistance.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/topics")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = {"https://gwsystem.vercel.app", "http://localhost:3000", "https://greenwich-cms.vercel.app"})
public class TopicController {
    private final TopicService topicSer;

    @GetMapping("/all")
    public DataResponse getAllTopics(@RequestParam Integer pageNumber) {
        return topicSer.getAllTopic(pageNumber);
    }
//
//    @GetMapping("/filter")
//    public List<Major> getAllStudent(@RequestParam Long pageNumber, @RequestParam(value = "search") String search) {
//        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
//        String operationSetExper = Joiner.on("|")
//                .join(SearchOperation.SIMPLE_OPERATION_SET);
//        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
//        Matcher matcher = pattern.matcher(search + ",");
//        while (matcher.find()) {
//            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
//        }
//
//        Specification<Major> spec = builder.buildMajor();
//        return topicSer.searchMajorSpecification(pageNumber, spec);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTopicById(@PathVariable Long id) {
        return topicSer.getTopicById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> addTopic(@Valid @RequestBody TopicRequest topicRequest) {
        return topicSer.addTopic(topicRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseObject> editTopic(@Valid @RequestBody TopicRequest topicRequest, @PathVariable Long id) {
        return topicSer.editTopic(topicRequest, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteTopic(@PathVariable Long id) {
        return topicSer.deleteTopic(id);
    }
}


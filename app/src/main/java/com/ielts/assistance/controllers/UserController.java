package com.ielts.assistance.controllers;

import com.ielts.assistance.payload.request.EditUserRequest;
import com.ielts.assistance.payload.request.SignupRequest;
import com.ielts.assistance.payload.response.DataResponse;
import com.ielts.assistance.payload.response.ResponseObject;
import com.ielts.assistance.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = {"https://gwsystem.vercel.app", "http://localhost:3000", "https://greenwich-cms.vercel.app"})
public class UserController {
    private final UserService userSer;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return userSer.addUser(signUpRequest);
    }


//    @GetMapping("/filter")
//    public DataResponse getAllStudent(@RequestParam Integer pageNumber, @RequestParam(value = "search") String search) {
//        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
//        String operationSetExper = Joiner.on("|")
//                .join(SearchOperation.SIMPLE_OPERATION_SET);
//        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
//        Matcher matcher = pattern.matcher(search + ",");
//        while (matcher.find()) {
//            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
//        }
//
//        Specification<User> spec = (Specification<User>) builder.build();
//        return userSer.searchUserSpecification(pageNumber, spec);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable UUID id){
        return userSer.deleteUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseObject> updateUser(@Valid @RequestBody EditUserRequest user, @PathVariable UUID id){
        return  userSer.updateUser(user,id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse getAllUser(@RequestParam int pageNumber) {
        return userSer.getAllUser(pageNumber);
    }
}

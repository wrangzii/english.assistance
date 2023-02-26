package com.ielts.assistance.service;

import com.ielts.assistance.models.User;
import com.ielts.assistance.payload.request.*;
import com.ielts.assistance.payload.response.DataResponse;
import com.ielts.assistance.payload.response.ResponseObject;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface UserService {
    DataResponse getAllUser(int pageNumber);
//    DataResponse searchUserSpecification(Integer pageNumber, Specification<User> spec);
    ResponseEntity<ResponseObject> addUser(SignupRequest signupRequest);
    ResponseEntity<ResponseObject> login(LoginRequest loginRequest, HttpServletResponse response);
    ResponseEntity<ResponseObject> deleteUser(UUID id);
    ResponseEntity<ResponseObject> updateUser(EditUserRequest user, UUID id);
    ResponseEntity<ResponseObject> forgotPassword(ForgotPasswordRequest user);
    ResponseEntity<ResponseObject> resetUserPassword(ResetUserPassword user, String confirmationToken);
}

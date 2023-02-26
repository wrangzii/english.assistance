package com.ielts.assistance.controllers;

import com.ielts.assistance.payload.request.ForgotPasswordRequest;
import com.ielts.assistance.payload.request.LoginRequest;
import com.ielts.assistance.payload.request.ResetUserPassword;
import com.ielts.assistance.payload.response.ResponseObject;
import com.ielts.assistance.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = {"https://gwsystem.vercel.app", "http://localhost:3000", "https://greenwich-cms.vercel.app"})
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "test", description = "test")
public class AuthController {
    private final UserService userSer;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userSer.login(loginRequest,response);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<ResponseObject> forgotUserPassword(@RequestBody ForgotPasswordRequest user) {
        return userSer.forgotPassword(user);
    }

    @PostMapping("/confirm_reset")
    public ResponseEntity<ResponseObject> resetUserPassword(@RequestParam("token")String confirmationToken, @RequestBody ResetUserPassword user) {
        return userSer.resetUserPassword(user, confirmationToken);
    }
}

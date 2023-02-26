package com.ielts.assistance.service.serviceImpl;

import com.ielts.assistance.models.ERole;
import com.ielts.assistance.models.Role;
import com.ielts.assistance.models.User;
import com.ielts.assistance.models.UserPlan;
import com.ielts.assistance.payload.request.*;
import com.ielts.assistance.payload.response.DataResponse;
import com.ielts.assistance.payload.response.JwtResponse;
import com.ielts.assistance.payload.response.ResponseObject;
import com.ielts.assistance.repository.RoleRepository;
import com.ielts.assistance.repository.UserRepository;
import com.ielts.assistance.security.jwt.JwtUtils;
import com.ielts.assistance.security.service.UserDetailsImpl;
import com.ielts.assistance.service.EmailSenderService;
import com.ielts.assistance.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    @Qualifier("passwordEncoder")
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailSenderService emailSenderService;
    public static final String USER = "USER";

    @Override
    public DataResponse getAllUser(int pageNumber) {
        int pageSize = 10;
        Pageable paging = PageRequest.of(pageNumber,pageSize);
        Page<User> pagedResult = userRepo.findAll(paging);
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
//    public DataResponse searchUserSpecification(Integer pageNumber, Specification<User> spec) {
//        int pageSize = 15;
//        Pageable paging = PageRequest.of(pageNumber, pageSize);
//        Page<User> pagedResult = userRepo.findAll(spec, paging);
//        DataResponse data = new DataResponse();
//        if (pagedResult.hasContent()) {
//            data.setData(pagedResult.getContent());
//            data.setPageNumber(pagedResult.getTotalPages());
//        }
//        return data;
//    }

    @Override
    public ResponseEntity<ResponseObject> addUser(SignupRequest signUpRequest) {
        if (userRepo.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Error: Username is already taken!"));
        }
        if (userRepo.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(),"Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getEmail()
                , signUpRequest.getUsername(), signUpRequest.getFullName(), signUpRequest.getPhoneNumber(), signUpRequest.getDob()
                , encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Date date = new Date();
        validateRole(strRoles, roles);
        UserPlan userPlan = new UserPlan();
        userPlan.setPlanId(1L);
        user.setPlanId(userPlan);
        user.setRoles(roles);
        user.setDob(signUpRequest.getDob());
        user.setCreateDate(date);
        user.setCreateBy(signUpRequest.getCreateBy());
        user.setIsActive(true);
        userRepo.save(user);
        return ResponseEntity.ok(new ResponseObject(HttpStatus.CREATED.toString(),"User registered successfully!", user));
    }

    @Override
    public ResponseEntity<ResponseObject> login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            JwtResponse userResponse = new JwtResponse(jwt,
                    userDetails.getUserId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getPhoneNumber(),
                    userDetails.getDateOfBirth(),
                    userDetails.getFullName(),
                    roles);
            Cookie cookie = new Cookie("token", jwt);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            response.addHeader("Authorization", jwt);
            return ResponseEntity.ok(new ResponseObject(userResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(),"Username or password is incorrect!"));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteUser(UUID id) {
        Optional<User> deleteUser = userRepo.findById(id);
        if (deleteUser.isPresent()) {
            deleteUser.get().setIsActive(false);
            userRepo.save(deleteUser.get());
            return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(),"Delete user successfully!"));
        }
        return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.NOT_FOUND.toString(),"User is not exist"));
    }

    @Override
    public ResponseEntity<ResponseObject> updateUser(EditUserRequest user, UUID id) {
        Optional<User> editUser = userRepo.findById(id);
        if (editUser.isPresent()){
            editUser.get().setEmail(user.getEmail());
            editUser.get().setFullName(user.getFullName());
            editUser.get().setPhoneNumber(user.getPhoneNumber());
            editUser.get().setIsActive(true);
            editUser.get().setModifyDate(new Date());
            editUser.get().setModifyBy(user.getModifyBy());
            Set<String> strRoles = user.getRole();
            Set<Role> roles = new HashSet<>();
            validateRole(strRoles, roles);
            editUser.get().setRoles(roles);
            userRepo.save(editUser.get());
            return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(), "Edit user successfully!", editUser));
        }
        return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.NOT_FOUND.toString(),"User is not exist"));
    }

    @Override
    public ResponseEntity<ResponseObject> forgotPassword(ForgotPasswordRequest user) {
        Optional<User> existedUser = userRepo.findByEmail(user.getEmail());
        if (existedUser.isPresent()) {
            // Save it
            existedUser.get().setResetPasswordToken(UUID.randomUUID().toString());
            userRepo.save(existedUser.get());

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existedUser.get().getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("test-email@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:3000/confirm_reset?token=" + existedUser.get().getResetPasswordToken());

            // Send the email
            emailSenderService.sendEmail(mailMessage);

            return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(), "Request to reset password received. Check your inbox for the reset link."));

        } else {
            return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.OK.toString(), "This email address does not exist!"));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> resetUserPassword(ResetUserPassword user, String confirmationToken) {
        User resetPasswordToken = userRepo.findByResetPasswordToken(confirmationToken);

        if (resetPasswordToken != null) {
            Optional<User> userReset = userRepo.findByEmail(resetPasswordToken.getEmail());
            // Use email to find user
            if (userReset.isPresent()) {
                userReset.get().setPassword(encoder.encode(user.getPassword()));
                userRepo.save(userReset.get());
            }
            return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(), "Password successfully reset. You can now log in with the new credentials."));
        } else {
            return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), "The link is invalid or broken!"));
        }
    }

    private void validateRole(Set<String> strRoles, Set<Role> roles) {
        if (strRoles.isEmpty()) {
            Role userRole = roleRepo.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepo.findByRoleName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepo.findByRoleName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
    }
}


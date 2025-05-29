package com.saeyesss.Tasks.service.impl;

import com.saeyesss.Tasks.dto.Response;
import com.saeyesss.Tasks.dto.UserRequest;
import com.saeyesss.Tasks.entity.User;
import com.saeyesss.Tasks.enums.Role;
import com.saeyesss.Tasks.exceptions.BadRequestException;
import com.saeyesss.Tasks.exceptions.NotFoundException;
import com.saeyesss.Tasks.repo.UserRepository;
import com.saeyesss.Tasks.security.JwtUtils;
import com.saeyesss.Tasks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Response<?> signUp(UserRequest userRequest) {
        log.info("signUp method called");
        Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());

        if (existingUser.isPresent()) {
            throw new BadRequestException("Username already taken");
        }
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Sign up successful")
                .build();
    }

    @Override
    public Response<?> login(UserRequest userRequest) {
        log.info("signIn method called");
        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }

        String token = jwtUtils.generateToken(user.getUsername());
        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Logged in successfully")
                .data(token)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }
}

package com.pharmadesk.backend.controller;

import com.pharmadesk.backend.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final com.pharmadesk.backend.repository.UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, com.pharmadesk.backend.repository.UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password")));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        com.pharmadesk.backend.model.User user = userRepository.findByUsername(loginRequest.get("username"))
                .orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException("User not found"));

        return ResponseEntity.ok(Map.of(
            "token", jwt,
            "user", Map.of(
                "username", user.getUsername(),
                "role", user.getRole(),
                "name", user.getName() != null ? user.getName() : user.getUsername()
            )
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok(Map.of("message", "User logged out successfully"));
    }
}

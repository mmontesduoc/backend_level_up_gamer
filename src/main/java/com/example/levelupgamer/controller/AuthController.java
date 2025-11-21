package com.example.levelupgamer.controller;

import com.example.levelupgamer.config.JwtUtil;
import com.example.levelupgamer.model.User;
import com.example.levelupgamer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User u) {
        userService.register(u);
        return "Usuario creado";
    }

    @PostMapping("/login")
    public String login(@RequestBody User u) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
        if (auth.isAuthenticated()) {
            return jwtUtil.generateToken(u.getUsername());
        }
        throw new RuntimeException("No autenticado");
    }
}
